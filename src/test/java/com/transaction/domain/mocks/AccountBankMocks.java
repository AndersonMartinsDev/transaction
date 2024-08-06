package com.transaction.domain.mocks;

import com.transaction.domain.entities.AccountBank;

import java.math.BigDecimal;

public class AccountBankMocks {

    public static AccountBank mock(String accountNumber, String mccDescription, String[] mccs, BigDecimal ammount){
        var account = new AccountBank();
        account.setAccountNumber(accountNumber);
        account.setMccDescription(mccDescription);
        account.setMcc(mccs);
        account.setAmmount(ammount);
        account.setBalance(ammount);
        return account;
    }
}
