package com.transaction.domain.services;

import com.transaction.domain.entities.AccountBank;
import com.transaction.domain.entities.Transaction;
import com.transaction.domain.enums.CodeReturnEnum;
import com.transaction.domain.exceptions.TransactionException;
import com.transaction.domain.repositories.AccountBankRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class AccountService {

    private final AccountBankRepository accountBankRepository;

    @Autowired
    public AccountService(AccountBankRepository accountBankRepository) {
        this.accountBankRepository = accountBankRepository;
    }

    public void save(List<AccountBank> accountBank) {
        accountBankRepository.saveAll(accountBank);
    }

    @SneakyThrows
    public void subtractBalance(Transaction transaction) {

        var accountList = accountBankRepository.findByAccountNumberAndMccLike(transaction.getAccount(), transaction.getMcc());

        if (Objects.isNull(accountList) || accountList.isEmpty()) {
            //TODO: VERIFICAR VALIDACAO DE MCC APARTIR DO NOME DO ESTABELECIMENTO
            //TODO: CRIAR ALGO PARA TRABALHAR COM CONCORRENCIA SE DUAS PESSOAS PASSAREM O CARTÃO AO MESMO TEMPO
            //TODO: Criar um código especifico para conta inexistente
            throw new TransactionException(CodeReturnEnum.NO_EXIST_ACCOUNT);
        }

        var accountMccTransaction = accountList
                                        .stream()
                                        .filter(acc -> filterMcc(acc.getMcc(), transaction.getMcc()))
                                        .findFirst();

        accountMccTransaction.ifPresentOrElse(
                acc -> accountBankRepository.save(minusBalance(acc, transaction)),
                () -> accountList
                        .stream()
                        .filter(acc -> "CASH".equalsIgnoreCase(acc.getMccDescription()))
                        .findFirst()
                        .ifPresent(acc -> accountBankRepository.save(minusBalance(acc, transaction)))
        );
    }

    private AccountBank minusBalance(AccountBank accountBank, Transaction transaction) {
        if (transaction.getTotalAmount().compareTo(accountBank.getBalance()) == 1) {
            throw new TransactionException(CodeReturnEnum.REJECTED);
        }
        accountBank.setBalance(accountBank.getBalance().subtract(transaction.getTotalAmount()));
        return accountBank;
    }

    private Boolean filterMcc(String[] mccs, String mcc) {
        return Arrays.binarySearch(mccs, mcc) != -1 ? Boolean.TRUE : Boolean.FALSE;
    }
}
