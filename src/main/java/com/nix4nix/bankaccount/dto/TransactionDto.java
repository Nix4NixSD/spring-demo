package com.nix4nix.bankaccount.dto;

import com.nix4nix.bankaccount.entity.Transaction;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private Long id;

    private Transaction.TransactionTypes type;

    private BigDecimal amount;

    private BigDecimal balance;

    private LocalDateTime createdAt;

    private String description;

    private Long accountId;
}
