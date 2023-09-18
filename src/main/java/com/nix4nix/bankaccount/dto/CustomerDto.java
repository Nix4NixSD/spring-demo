package com.nix4nix.bankaccount.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerDto {
    private Long customerId;

    private String name;

    private String surname;

    private String email;

    private String phone;

    private List<AccountDto> accounts = new ArrayList<>();
}
