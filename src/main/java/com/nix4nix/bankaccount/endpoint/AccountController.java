package com.nix4nix.bankaccount.endpoint;

import com.nix4nix.bankaccount.dto.AccountDto;
import com.nix4nix.bankaccount.dto.CreateAccountDto;
import com.nix4nix.bankaccount.dto.TransactionDto;
import com.nix4nix.bankaccount.entity.Account;
import com.nix4nix.bankaccount.entity.Transaction;
import com.nix4nix.bankaccount.service.AccountService;
import com.nix4nix.bankaccount.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping(AccountController.BASE_URL)
public class AccountController {
    static final String BASE_URL = "/api/account";

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    /**
     * Will open a new account and initiate a transaction whenever the given Initial credit is greater than 0.
     * @param createAccountDto CreateAccountDto
     * @return ResponseEntity<AccountDto>
     */
    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody CreateAccountDto createAccountDto) {
        AccountDto accountDto = new AccountDto();
        accountDto.setOwnerId(createAccountDto.getCustomerId());

        // Validate and get the correct Account.AccountType enum value
        Account.AccountTypes type = accountService.validateAccountType(createAccountDto.getAccountType());
        accountDto.setType(type);

        // Update account with new balance.
        accountDto.setBalance(createAccountDto.getInitialCredit());
        AccountDto accountResult = accountService.create(accountDto);

        // Check if the initialCredit is greater than zero because then we need to initiate a transaction
        if (createAccountDto.getInitialCredit().compareTo(BigDecimal.ZERO) > 0) {
            /*
            Due to the time restrain on this assignment i will just create a transaction and update the account.
            In a real world example this should be an actual database transaction where you can rollback if one of
            the prerequisites does not match.

            Maybe create a class "TransactionResolver" that is responsible for handling those transactions. Where checks
            will be done and the addition/subtraction of the amounts from/to the accounts.
             */

            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setType(Transaction.TransactionTypes.ADD);
            transactionDto.setCreatedAt(LocalDateTime.now());
            transactionDto.setBalance(BigDecimal.ZERO); // New account so the balance is zero.
            transactionDto.setAmount(createAccountDto.getInitialCredit());
            transactionDto.setAccountId(accountResult.getId());
            transactionService.create(transactionDto);

            // Fetch the account to also get the new transaction data
            accountResult = accountService.get(accountResult.getId());
        }

        return new ResponseEntity<>(accountResult, HttpStatus.OK);
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
