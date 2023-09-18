package com.nix4nix.bankaccount.entity;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class Account {
    private Long accountId;

    private final String accountNumber;

    private final BigDecimal balance;

    private final String type;

    private final LocalDateTime createdAt;

    private final Customer owner;

    public Account(String accountNumber, BigDecimal balance, String type, LocalDateTime createdAt, Customer owner) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.type = type;
        this.createdAt = createdAt;
        this.owner = owner;
    }
}
