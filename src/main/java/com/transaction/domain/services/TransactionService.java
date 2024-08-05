package com.transaction.domain.services;

import com.transaction.domain.entities.Transaction;
import com.transaction.domain.enums.CodeReturnEnum;
import com.transaction.domain.exceptions.TransactionException;
import com.transaction.domain.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Semaphore;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final Semaphore semaphore = new Semaphore(1);

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public void createTransaction(Transaction transaction){
        try {
            semaphore.acquire();
            accountService.subtractBalance(transaction);
            transactionRepository.save(transaction);
        }catch (InterruptedException ex){
            throw new TransactionException(CodeReturnEnum.ANY_OTHER_PROBLEM);
        } finally {
            semaphore.release();
        }
    }
}
