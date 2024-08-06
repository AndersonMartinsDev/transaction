package com.transaction.domain.services;

import com.transaction.domain.entities.Transaction;

public  interface MCCIndentifyService {
    String verifyMccByName(Transaction transaction);
}
