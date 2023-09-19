package com.nix4nix.bankaccount.service;

import javax.persistence.Entity;
import java.util.Collection;

/**
 * All services should have a standard set of methods.
 */
public interface BaseService<T> {
    public abstract void create(T entity);
    public abstract void update(T entity);
    public abstract void delete(T entity);
    public abstract T get(Long id);
    public abstract Collection<T> getAll();
}
