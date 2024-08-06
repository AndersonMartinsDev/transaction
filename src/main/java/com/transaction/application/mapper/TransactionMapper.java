package com.transaction.application.mapper;

import com.transaction.application.dto.TransactionDto;
import com.transaction.domain.entities.Transaction;

public class TransactionMapper {
    private TransactionMapper() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static Transaction toTransaction(TransactionDto dto){
        var transaction = new Transaction();
        transaction.setAccount(dto.getAccount());
        transaction.setMcc(dto.getMcc());
        transaction.setMerchant(dto.getMerchant());
        transaction.setTotalAmount(dto.getTotalAmount());
        return transaction;
    }
}
