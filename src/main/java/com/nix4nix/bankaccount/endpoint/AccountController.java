package com.nix4nix.bankaccount.endpoint;

import com.nix4nix.bankaccount.dto.AccountDto;
import com.nix4nix.bankaccount.dto.CreateAccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AccountController.BASE_URL)
public class AccountController {
    static final String BASE_URL = "/api/account";

    public ResponseEntity<AccountDto> createAccount(@RequestBody CreateAccountDto createAccountDto) {
        //TODO: Do the necessary steps and return the result
        return new ResponseEntity<>(new AccountDto(), HttpStatus.OK);
    }
}
