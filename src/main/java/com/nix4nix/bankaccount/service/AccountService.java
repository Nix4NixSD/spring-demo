package com.nix4nix.bankaccount.service;

import com.nix4nix.bankaccount.entity.Account;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public class AccountService implements BaseService<Account> {

    @Override
    public void create(Account entity) {

    }

    @Override
    public void update(Account entity) {

    }

    @Override
    public void delete(Account entity) {

    }

    @Override
    public Account get(Long id) {
        return null;
    }

    @Override
    public Collection<Account> getAll() {
        return null;
    }
}
