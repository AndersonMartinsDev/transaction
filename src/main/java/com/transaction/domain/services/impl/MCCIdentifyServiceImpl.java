package com.transaction.domain.services.impl;

import com.transaction.domain.entities.Transaction;
import com.transaction.domain.repositories.AccountBankRepository;
import com.transaction.domain.services.MCCIndentifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.transaction.domain.util.AssociateWordsWithMccUtil.getMcc;

@Service
public class MCCIdentifyServiceImpl implements MCCIndentifyService {

    private final AccountBankRepository accountBankRepository;

    @Autowired
    public MCCIdentifyServiceImpl(AccountBankRepository accountBankRepository) {
        this.accountBankRepository = accountBankRepository;
    }

    @Override
    public String verifyMccByName(Transaction transaction){
        var mcc = transaction.getMcc();

        var exitsMcc = accountBankRepository.existsByMccLike(mcc);

        if (!exitsMcc) {
            mcc = getMcc(transaction.getMerchant());
        }
        return mcc;
    }
}
