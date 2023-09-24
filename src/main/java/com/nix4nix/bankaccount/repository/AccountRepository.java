package com.nix4nix.bankaccount.repository;

import com.nix4nix.bankaccount.entity.Account;
import com.nix4nix.bankaccount.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /*
    Returns all accounts which are owned by the given customer.
     */
    Collection<Account> findAllByOwner(Customer owner);
}
