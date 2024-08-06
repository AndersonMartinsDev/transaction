package com.transaction.domain.services.impl;

import com.transaction.domain.entities.Transaction;
import com.transaction.domain.exceptions.TransactionException;
import com.transaction.domain.mocks.TransactionMocks;
import com.transaction.domain.repositories.AccountBankRepository;
import com.transaction.domain.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TransactionServiceImplTest {

    @Mock
    private AccountBankRepository accountBankRepository;

    @Mock
    private MCCIdentifyServiceImpl mccIdentifyServiceImpl;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountServiceImpl accountServiceImpl;

    @Mock
    private Semaphore semaphore;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction validTransaction;

    @BeforeEach
    public void setUp() {
        validTransaction = TransactionMocks.mocks("1234", "123456","RESTAURANT", new BigDecimal(100));
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTransaction_success_shouldSaveTransaction(){
        doNothing().when(accountServiceImpl).subtractBalance(validTransaction);
        transactionService.createTransaction(validTransaction);
        verify(transactionRepository).save(validTransaction);
    }

    @Test
    public void testCreateTransaction_accountServiceThrowsException_shouldPropagateException(){
        when(mccIdentifyServiceImpl.verifyMccByName(ArgumentMatchers.any())).thenReturn("123");
        when(accountBankRepository.findByAccountNumberAndMccLike(ArgumentMatchers.any(), ArgumentMatchers.anyString())).thenReturn(null);
        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}
