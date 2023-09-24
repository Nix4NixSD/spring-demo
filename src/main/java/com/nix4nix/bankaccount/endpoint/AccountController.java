package com.nix4nix.bankaccount.endpoint;

import com.nix4nix.bankaccount.controlleradvice.exception.NotImplementedException;
import com.nix4nix.bankaccount.dto.AccountDto;
import com.nix4nix.bankaccount.dto.CreateAccountDto;
import com.nix4nix.bankaccount.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(AccountController.BASE_URL)
public class AccountController {
    static final String BASE_URL = "/api/account";

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody CreateAccountDto createAccountDto) {
        throw new NotImplementedException(BASE_URL + "/create");
    }

    @GetMapping("/get")
    public ResponseEntity<Collection<AccountDto>> getAccounts() {
        return new ResponseEntity<>(accountService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{ownerId}")
    public ResponseEntity<Collection<AccountDto>> getAllForOwner(@PathVariable Long ownerId) {
        return new ResponseEntity<>(accountService.getAllById(ownerId), HttpStatus.OK);
    }
}
