package com.nix4nix.bankaccount.entity;

import lombok.Getter;

@Getter
public class Customer {
    private final Long customerId;

    private final String name;

    private final String surname;

    private final String email;

    private final String phone;

    public Customer(Long customerId, String name, String surname, String email, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }
}
