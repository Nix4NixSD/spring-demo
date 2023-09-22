package com.nix4nix.bankaccount.controlleradvice;

/**
 * Interface for out advice classes. All advice classes should have a handler to handle the exception.
 * Make sure to add the following annotations to the advice class implementing this interface:
 *
 * class annotation
 * ControllerAdvice
 *
 * Method annotations
 * ResponseBody
 * ExceptionHandler(CustomerNotFoundException.class)
 * ResponseStatus(HttpStatus.NOT_FOUND)
 */
public interface BaseAdvice<T extends RuntimeException> {
    public String handler(T e);
}
