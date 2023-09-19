package com.nix4nix.bankaccount.controlleradvice.exception;

/**
 * CustomerException is thrown when an customer is looked up but not found.
 * This exception is supposed to be caught by the CustomerNotFoundAdvice which is responsible for
 * rendering this exception into a response with the correct status-code and return that to sender.
 */
public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Long id) {
        super("Could not find customer " + id);
    }
}
