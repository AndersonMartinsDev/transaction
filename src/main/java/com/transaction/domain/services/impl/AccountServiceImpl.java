package com.transaction.domain.services.impl;

import com.transaction.domain.entities.AccountBank;
import com.transaction.domain.entities.Transaction;
import com.transaction.domain.enums.CodeReturnEnum;
import com.transaction.domain.exceptions.TransactionException;
import com.transaction.domain.repositories.AccountBankRepository;
import com.transaction.domain.services.AccountService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountBankRepository accountBankRepository;
    private final MCCIdentifyServiceImpl mccIdentifyServiceImpl;

    @Autowired
    public AccountServiceImpl(AccountBankRepository accountBankRepository, MCCIdentifyServiceImpl mccIdentifyServiceImpl) {
        this.accountBankRepository = accountBankRepository;
        this.mccIdentifyServiceImpl = mccIdentifyServiceImpl;
    }
    @Override
    public void save(List<AccountBank> accountBank) {
        accountBankRepository.saveAll(accountBank);
    }

    @Override
    @SneakyThrows
    public void subtractBalance(Transaction transaction) {

        var mcc = mccIdentifyServiceImpl.verifyMccByName(transaction);

        var accountList = accountBankRepository.findByAccountNumberAndMccLike(transaction.getAccount(), mcc);

        if (Objects.isNull(accountList) || accountList.isEmpty()) {
            throw new TransactionException(CodeReturnEnum.NO_EXIST_ACCOUNT);
        }

        var accountMccTransaction = accountList
                .stream()
                .filter(acc -> filterMcc(acc.getMcc(), transaction.getMcc()))
                .findFirst();

        accountMccTransaction.ifPresentOrElse(
                acc -> {
                    if (existBalance(transaction, acc)) {
                        minusBalance(acc, transaction);
                    }else{
                        accountList
                                .stream()
                                .filter(accountBank -> "CASH".equalsIgnoreCase(accountBank.getMccDescription()))
                                .findFirst()
                                .ifPresent(accountBank -> rulesUnderCash(accountBank, transaction));
                    }
                },
                () -> {
                    throw new TransactionException(CodeReturnEnum.REJECTED);
                }
        );
    }

    private void rulesUnderCash(AccountBank cash, Transaction transaction) {
        if (!existBalance(transaction, cash)) {
            throw new TransactionException(CodeReturnEnum.REJECTED);
        }
        minusBalance(cash, transaction);
    }

    private Boolean existBalance(Transaction transaction, AccountBank accountBank) {
        if (transaction.getTotalAmount().compareTo(accountBank.getBalance()) == 1) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private void minusBalance(AccountBank accountBank, Transaction transaction) {
        accountBank.setBalance(accountBank.getBalance().subtract(transaction.getTotalAmount()));
        accountBankRepository.save(accountBank);
    }

    private Boolean filterMcc(String[] mccs, String mcc) {
        return Arrays.binarySearch(mccs, mcc) >= 0 ? Boolean.TRUE : Boolean.FALSE;
    }
}
