package com.nix4nix.bankaccount.controlleradvice;

import com.nix4nix.bankaccount.controlleradvice.exception.AccountMalformedException;
import com.nix4nix.bankaccount.controlleradvice.exception.CustomerNotFoundException;
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
public class AccountMalformedAdvice implements BaseAdvice<AccountMalformedException> {

    @ResponseBody
    @ExceptionHandler(AccountMalformedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handler(AccountMalformedException e) {
        return e.getMessage();
    }
}
