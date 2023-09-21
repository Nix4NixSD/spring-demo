package com.nix4nix.bankaccount.dto;

import com.nix4nix.bankaccount.entity.Customer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerDto {
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String phone;

    private List<AccountDto> accounts = new ArrayList<>();
}
