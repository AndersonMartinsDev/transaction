package com.transaction.domain.services.impl;

import com.transaction.domain.entities.AccountBank;
import com.transaction.domain.entities.Transaction;
import com.transaction.domain.exceptions.TransactionException;
import com.transaction.domain.mocks.AccountBankMocks;
import com.transaction.domain.mocks.TransactionMocks;
import com.transaction.domain.repositories.AccountBankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class AccountServiceImplTest {

    @Mock
    private AccountBankRepository accountBankRepository;

    @Mock
    private MCCIdentifyServiceImpl mccIdentifyServiceImpl;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Transaction validTransaction;
    private List<AccountBank> accountList;
    private Set<AccountBank> accountSet;

    private AccountBank accountBank1;
    private AccountBank accountBank2;
    private AccountBank accountBank3;

    @BeforeEach
    public void setUp() {
        validTransaction = TransactionMocks.mocks("456", "000001", "RESTAURANT", new BigDecimal(100));
        accountList = new ArrayList<>();
        accountSet = new HashSet<>();
        accountBank1 = AccountBankMocks.mock("000001", "SAVINGS", new String[]{"123"}, new BigDecimal(200));
        accountBank2 = AccountBankMocks.mock("000002", "CURRENT", new String[]{"456", "789"}, new BigDecimal(50));
        accountBank3 = AccountBankMocks.mock("000003", "CASH", new String[]{"---"}, new BigDecimal(50));
        accountSet.add(accountBank1);
        accountSet.add(accountBank2);
        accountSet.add(accountBank3);
        accountList.add(accountBank1);
        accountList.add(accountBank1);
        accountList.add(accountBank3);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave_shouldSaveAllAccounts() {
        accountService.save(accountList);

        verify(accountBankRepository).saveAll(accountList);
    }

    @Test
    public void testSubtractBalance_noMatchingAccount_shouldThrowTransactionException() {
        when(mccIdentifyServiceImpl.verifyMccByName(validTransaction)).thenReturn("123");
        when(accountBankRepository.findByAccountNumberAndMccLike(validTransaction.getAccount(), "123")).thenReturn(new HashSet<>());

        assertThrows(TransactionException.class, () -> accountService.subtractBalance(validTransaction));
        verify(accountBankRepository, never()).save(any(AccountBank.class));
    }

    @Test
    public void testSubtractBalance_insufficientFunds_shouldThrowTransactionException() {
        when(mccIdentifyServiceImpl.verifyMccByName(validTransaction)).thenReturn("123");
        when(accountBankRepository.findByAccountNumberAndMccLike(validTransaction.getAccount(), "123")).thenReturn(accountSet);

        validTransaction.setTotalAmount(new BigDecimal(300)); // Transaction amount exceeds balance

        assertThrows(TransactionException.class, () -> accountService.subtractBalance(validTransaction));
        verify(accountBankRepository, never()).save(any(AccountBank.class));
    }

    @Test
    public void testSubtractBalance_noMatchingMcc_shouldFallbackToCashAccount() {
        when(mccIdentifyServiceImpl.verifyMccByName(validTransaction)).thenReturn("789"); // Mismatched MCC
        when(accountBankRepository.findByAccountNumberAndMccLike(validTransaction.getAccount(), "789")).thenReturn(accountSet);
        when(accountBankRepository.findByAccountNumberAndMccLike(validTransaction.getAccount(), "CASH")).thenReturn(Set.of(AccountBankMocks.mock("000003", "CASH", null, new BigDecimal(500))));

    }
}
