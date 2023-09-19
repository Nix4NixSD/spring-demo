package com.nix4nix.bankaccount.entity;

import lombok.*;

import javax.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Entity
@Table(name = "transaction")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final BigDecimal amount;

    private final BigDecimal balance;

    private final LocalDateTime createdAt;

    private final String description;

    @ManyToOne
    private final Account account;

}
