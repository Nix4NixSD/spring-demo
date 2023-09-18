package com.nix4nix.bankaccount.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Entity
@Table(name = "account")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private final String accountNumber;

    private final BigDecimal balance;

    private final String type;

    private final LocalDateTime createdAt;

    @ManyToOne
    private final Customer owner;
}
