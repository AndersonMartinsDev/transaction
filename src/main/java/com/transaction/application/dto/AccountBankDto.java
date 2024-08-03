package com.transaction.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class AccountBankDto {
        private String accountNumber;
        private BigDecimal ammount;
        private BigDecimal balance;
        private String[] mcc;
        private String mccDescription;
}
