package com.nix4nix.bankaccount.controlleradvice.exception;

/**
 * NotImplementedException is thrown when a endpoint is called that is not implemented yet.
 * This exception is supposed to be caught by the CustomerNotFoundAdvice which is responsible for
 * rendering this exception into a response with the correct status-code and return that to sender.
 */
public class NotImplementedException extends RuntimeException {
    public NotImplementedException(String endPoint) {
        super("This endpoint is not implemented yet " + endPoint);
    }
}
