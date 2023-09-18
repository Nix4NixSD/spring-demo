package com.nix4nix.bankaccount.entity;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class Transaction {
    private Long transactionId;

    private final BigDecimal amount;

    private final BigDecimal balance;

    private final LocalDateTime createdAt;

    private final String description;

    private final Account account;

    public Transaction(BigDecimal amount, BigDecimal balance, LocalDateTime createdAt, String description, Account account) {
        this.amount = amount;
        this.balance = balance;
        this.createdAt = createdAt;
        this.description = description;
        this.account = account;
    }
}
