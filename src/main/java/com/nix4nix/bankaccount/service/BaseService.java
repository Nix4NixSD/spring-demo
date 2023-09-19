package com.nix4nix.bankaccount.service;

import javax.persistence.Entity;
import java.util.Collection;

/**
 * All services should have a standard set of methods.
 */
public interface BaseService {
    public abstract void create(Entity entity);
    public abstract void update(Entity entity);
    public abstract void delete(Entity entity);
    public abstract Entity get(Long id);
    public abstract Collection<Entity> getAll();
}
