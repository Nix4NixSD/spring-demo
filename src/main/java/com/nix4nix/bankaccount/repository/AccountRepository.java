package com.nix4nix.bankaccount.repository;

import com.nix4nix.bankaccount.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
