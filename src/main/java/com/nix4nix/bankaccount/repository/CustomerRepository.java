package com.nix4nix.bankaccount.repository;

import com.nix4nix.bankaccount.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
