package com.transaction.domain.services;

import com.transaction.domain.entities.AccountBank;
import com.transaction.domain.entities.Transaction;

import java.util.List;

public interface AccountService {
    void save(List<AccountBank> accountBank);
    void subtractBalance(Transaction transaction);
}
