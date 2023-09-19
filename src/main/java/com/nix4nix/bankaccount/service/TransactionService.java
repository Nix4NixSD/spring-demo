package com.nix4nix.bankaccount.service;

import javax.persistence.Entity;
import javax.transaction.Transactional;
import java.util.Collection;

@Transactional
public class TransactionService implements BaseService {
    @Override
    public void create(Entity entity) {

    }

    @Override
    public void update(Entity entity) {

    }

    @Override
    public void delete(Entity entity) {

    }

    @Override
    public Entity get(Long id) {
        return null;
    }

    @Override
    public Collection<Entity> getAll() {
        return null;
    }
}
