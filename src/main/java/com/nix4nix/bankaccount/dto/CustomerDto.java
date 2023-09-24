package com.nix4nix.bankaccount.dto;

import lombok.Data;
import java.util.Collection;

@Data
public class CustomerDto {
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String phone;

    private Collection<AccountDto> accounts;
}
