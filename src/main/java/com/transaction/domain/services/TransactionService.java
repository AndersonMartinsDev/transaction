package com.transaction.domain.services;

import com.transaction.domain.entities.Transaction;

public interface TransactionService {
    void createTransaction(Transaction transaction);
}
