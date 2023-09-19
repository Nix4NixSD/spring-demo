package com.nix4nix.bankaccount.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Data
@Entity
@Table(name = "customer")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    private final String name;

    private final String surname;

    private final String email;

    private final String phone;

}
