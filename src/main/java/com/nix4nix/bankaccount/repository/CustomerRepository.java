package com.nix4nix.bankaccount.repository;

import com.nix4nix.bankaccount.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> { }
