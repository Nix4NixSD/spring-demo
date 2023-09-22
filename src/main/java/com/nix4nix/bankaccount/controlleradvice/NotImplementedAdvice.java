package com.nix4nix.bankaccount.controlleradvice;

import com.nix4nix.bankaccount.controlleradvice.exception.CustomerNotFoundException;
import com.nix4nix.bankaccount.controlleradvice.exception.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Config for the CustomerNotFoundException.
 * This will respond whenever a CustomerNotFoundException is thrown.
 * Renders the exception straight into the response body with the status 404.
 */
@ControllerAdvice
public class NotImplementedAdvice implements BaseAdvice<NotImplementedException> {

    @ResponseBody
    @ExceptionHandler(NotImplementedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String handler(NotImplementedException e) {
        return e.getMessage();
    }
}
