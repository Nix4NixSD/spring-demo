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
    public enum AccountTypes {
        BETAALREKENING,
        SPAARREKENING,
        HYPOTHEEKREKENING,
        LENINGREKENING
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final String accountNumber;

    private final BigDecimal balance;

    // In a real world example we should probably move this to a separate table/entity
    @Enumerated(EnumType.STRING)
    private final AccountTypes type;

    private final LocalDateTime createdAt;

    @ManyToOne
    private final Customer owner;

}