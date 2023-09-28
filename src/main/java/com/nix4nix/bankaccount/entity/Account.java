package com.nix4nix.bankaccount.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Let's say our api does a lot of searching on accountNumber, which is quite realistic for an api involving accounts
 * and transactions. If we put an index on the accountNumber performance probably improves. Adding an index will result in:
 *
 * Faster Data Retrieval
 * Improved Query Performance
 * Reduced disk I/O
 *
 * And many more depending on the situation and if our application will do a lot for sorting and searching.
 */
@Data
@Entity
@Table(name = "account", indexes = {
        @Index(name = "idx_account_number", columnList = "accountNumber")
})
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
public class Account {
    public enum AccountTypes {
        BETAALREKENING,
        SPAARREKENING,
        HYPOTHEEKREKENING,
        LENINGREKENING
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 18, nullable = false)
    private final String accountNumber;

    private final BigDecimal balance;

    // In a real world example we should probably move this to a separate table/entity
    @Enumerated(EnumType.STRING)
    private final AccountTypes type;

    private final LocalDateTime createdAt;

    @ManyToOne
    private final Customer owner;

}