package com.nix4nix.bankaccount.service;

import com.nix4nix.bankaccount.controlleradvice.exception.AccountNotFoundException;
import com.nix4nix.bankaccount.dto.AccountDto;
import com.nix4nix.bankaccount.entity.Account;
import com.nix4nix.bankaccount.entity.Transaction;
import com.nix4nix.bankaccount.repository.AccountRepository;
import com.nix4nix.bankaccount.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * An example class on how the transactions could be done using a handler class.
 * This class is somewhat different from the normal services classes and it can only make withdrawals or Deposits.
 *
 * This TransactionHandler should only be used by other services that does something with transactions and need to
 * handle them. The responsibility of this class is only to make a deposit or withdrawal, update the account balance and
 * throw exception when the transaction cannot be finished.
 */
@AllArgsConstructor
@Service
public class TransactionHandlerService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    //TODO: The mapping functions need to be centralized, currently importing a whole service just for the mapping.
    private final AccountService accountService;

    /**
     * If a problem ever arises springboot should rollback this @Transactional method.
     * Though before using this testing should be done.
     * @param accountId
     * @param amount
     */
    @Transactional
    public void makeDeposit(Long accountId, BigDecimal amount) {
        // Check if an account exists
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);

        // Convert to dto because entity fields are final.
        AccountDto dto = accountService.convertToDto(account);
        dto.setBalance(newBalance);

        // Convert back to entity and save the entity to the database.
        account = accountService.convertToEntity(dto);
        accountRepository.save(account);

        // Finally add the transaction
        Transaction transaction = new Transaction(
                Transaction.TransactionTypes.ADD,
                amount,
                currentBalance,
                LocalDateTime.now(),
                "Deposit to account " + account.getAccountNumber(),
                account
        );
        transactionRepository.save(transaction);
    }

    @Transactional
    public void makeWithdrawal(Long accountId, BigDecimal amount) {
        //TODO: Do the opposite of makeDeposit and add a check if the account haves enough balance.
    }

}
