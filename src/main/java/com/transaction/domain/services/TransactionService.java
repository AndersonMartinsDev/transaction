package com.transaction.domain.services;

import com.transaction.domain.entities.AccountBank;
import com.transaction.domain.entities.Transaction;
import com.transaction.domain.repositories.AccountBankRepository;
import com.transaction.domain.repositories.TransactionRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }


    @SneakyThrows
    public void createTransaction(Transaction transaction) {
        accountService.subtractBalance(transaction);
        transactionRepository.save(transaction);
    }
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
