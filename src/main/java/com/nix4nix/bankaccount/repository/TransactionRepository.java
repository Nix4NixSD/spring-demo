package com.nix4nix.bankaccount.repository;

import com.nix4nix.bankaccount.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
