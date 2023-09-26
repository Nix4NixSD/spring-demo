package com.nix4nix.bankaccount.controlleradvice.exception;

import com.nix4nix.bankaccount.dto.AccountDto;

/**
 * CustomerException is thrown when an customer is looked up but not found.
 * This exception is supposed to be caught by the CustomerNotFoundAdvice which is responsible for
 * rendering this exception into a response with the correct status-code and return that to sender.
 */
public class AccountMalformedException extends RuntimeException{
    public AccountMalformedException(AccountDto dto, String reason) {
        super("AccountDto is malformed. ".concat(reason) + " ".concat(dto.toString()));
    }
    public AccountMalformedException(String reason) {
        super("AccountDto is malformed. ".concat(reason));
    }
}
