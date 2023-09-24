package com.nix4nix.bankaccount.repository;

import com.nix4nix.bankaccount.entity.Account;
import com.nix4nix.bankaccount.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /*
    Returns all Transactions that are linked to the given account.
     */
    Collection<Transaction> findAllByAccount(Account account);
}
