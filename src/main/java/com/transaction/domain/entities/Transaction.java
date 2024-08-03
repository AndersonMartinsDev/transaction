package com.transaction.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Getter
@Setter
@Table(name="transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    private UUID uuid;
    @Column(name="account")
    private String account;
    @Column(name="total_amount")
    private BigDecimal totalAmount;
    @Column(name="mcc")
    private String mcc;
    @Column(name="merchant_description")
    private String merchant;
}
