package com.nix4nix.bankaccount.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountDto {
    private Long customerId;
    private String accountType;
    private BigDecimal initialCredit;
}
