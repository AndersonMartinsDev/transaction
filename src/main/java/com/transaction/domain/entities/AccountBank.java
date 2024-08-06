package com.transaction.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="account_bank")
public class AccountBank {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(name="account_number",length = 15)
    private String accountNumber;
    @Column(name="ammount")
    private BigDecimal ammount;
    @Column(name="balance")
    private BigDecimal balance;
    @Column(name="mcc",unique = true)
    private String[] mcc;
    @Column(name="mcc_description")
    private String mccDescription;
}
