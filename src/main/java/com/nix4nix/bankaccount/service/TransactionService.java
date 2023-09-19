package com.nix4nix.bankaccount.service;

import com.nix4nix.bankaccount.entity.Transaction;

import javax.persistence.Entity;
import javax.transaction.Transactional;
import java.util.Collection;

@Transactional
public class TransactionService implements BaseService<Transaction> {
    @Override
    public void create(Transaction entity) {

    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public void delete(Transaction entity) {

    }

    @Override
    public Transaction get(Long id) {
        return null;
    }

    @Override
    public Collection<Transaction> getAll() {
        return null;
    }
}
