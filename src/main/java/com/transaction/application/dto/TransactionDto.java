package com.transaction.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionDto {
    private String account;
    private BigDecimal totalAmount;
    private String mcc;
    private String merchant;
}
