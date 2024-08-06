package com.transaction.domain.mocks;

import com.transaction.domain.entities.Transaction;

import java.math.BigDecimal;

public class TransactionMocks {
    private TransactionMocks() {
    }

    public static Transaction mocks(String mcc, String account, String merchant, BigDecimal totalAmount){
        var transaction = new Transaction();
        transaction.setTotalAmount(totalAmount);
        transaction.setMcc(mcc);
        transaction.setAccount(account);
        transaction.setMerchant(merchant);
        return transaction;
    }
}
