package com.transaction.domain.services;

import com.transaction.domain.entities.Transaction;
import com.transaction.domain.repositories.AccountBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.transaction.domain.util.AssociateWordsWithMccUtil.getMcc;

@Service
public class MCCIdentifyService {

    private final AccountBankRepository accountBankRepository;

    @Autowired
    public MCCIdentifyService(AccountBankRepository accountBankRepository) {
        this.accountBankRepository = accountBankRepository;
    }

    public String verifyMccByName(Transaction transaction){
        var mcc = transaction.getMcc();

        var exitsMcc = accountBankRepository.existsByMccLike(mcc);

        if (!exitsMcc) {
            mcc = getMcc(transaction.getMerchant());
        }
        return mcc;
    }
}
