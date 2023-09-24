package com.nix4nix.bankaccount.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class AccountDto {
    private Long id;

    private String accountNumber;

    private BigDecimal balance;

    private String type;

    private LocalDateTime createdAt;

    private Long ownerId;

    private Collection<TransactionDto> transactions;
}
