package com.transaction.domain.services.impl;

import com.transaction.domain.entities.Transaction;
import com.transaction.domain.enums.CodeReturnEnum;
import com.transaction.domain.exceptions.TransactionException;
import com.transaction.domain.repositories.TransactionRepository;
import com.transaction.domain.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Semaphore;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountServiceImpl accountServiceImpl;
    private final Semaphore semaphore = new Semaphore(1);

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountServiceImpl accountServiceImpl) {
        this.transactionRepository = transactionRepository;
        this.accountServiceImpl = accountServiceImpl;
    }

    @Override
    public void createTransaction(Transaction transaction){
        try {
            semaphore.acquire();
            accountServiceImpl.subtractBalance(transaction);
            transactionRepository.save(transaction);
        }catch (InterruptedException ex){
            throw new TransactionException(CodeReturnEnum.ANY_OTHER_PROBLEM);
        } finally {
            semaphore.release();
        }
    }
}
