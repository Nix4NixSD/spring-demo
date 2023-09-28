package com.nix4nix.bankaccount.entity;

import lombok.*;

import javax.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transaction")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
public class Transaction {
    public enum TransactionTypes {
        ADD,
        SUBTRACT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private final Transaction.TransactionTypes type;

    // Amount to add or subtract
    private final BigDecimal amount;

    // current balance
    private final BigDecimal balance;

    private final LocalDateTime createdAt;

    private final String description;

    @ManyToOne
    private final Account account;

}
