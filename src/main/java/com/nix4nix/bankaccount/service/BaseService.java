package com.nix4nix.bankaccount.service;
import java.util.Collection;

/**
 * All services should have a standard set of methods.
 */
public interface BaseService<T, E> {
    public abstract void create(T dto);
    public abstract void update(T dto);
    public abstract void delete(T dto);
    public abstract T get(Long id);
    public abstract Collection<T> getAll();

    /*
    These convert methods should use the modelMapper to map model T to E and model E to T.
    The modelMapper is defined in the Java2Application.java and is available globally.
     */
    public T convertToDto(E entity);
    public E convertToEntity(T dto);
}
