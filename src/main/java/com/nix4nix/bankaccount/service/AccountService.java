package com.nix4nix.bankaccount.service;

import com.nix4nix.bankaccount.controlleradvice.exception.AccountMalformedException;
import com.nix4nix.bankaccount.controlleradvice.exception.AccountNotFoundException;
import com.nix4nix.bankaccount.controlleradvice.exception.CustomerNotFoundException;
import com.nix4nix.bankaccount.dto.AccountDto;
import com.nix4nix.bankaccount.dto.TransactionDto;
import com.nix4nix.bankaccount.entity.Account;
import com.nix4nix.bankaccount.entity.Customer;
import com.nix4nix.bankaccount.repository.AccountRepository;
import com.nix4nix.bankaccount.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.yaml.snakeyaml.util.EnumUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Random;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@AllArgsConstructor
@Service
public class AccountService implements BaseService<AccountDto, Account> {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    private TransactionService transactionService;

    private ModelMapper modelMapper;

    /**
     * Customer opens new account.
     * @param dto AccountDto
     * @return AccountDto result
     */
    @Override
    public AccountDto create(AccountDto dto) {
        dto.setAccountNumber(this.generateNewAccountNumber());
        if (!ObjectUtils.containsConstant(Account.AccountTypes.values(), dto.getType())) {
            throw new AccountMalformedException(dto, "Invalid AccountType given.");
        }
        dto.setBalance(new BigDecimal(0));
        dto.setCreatedAt(LocalDateTime.now());

        Account result = accountRepository.save(this.convertToEntity(dto));
        return this.convertToDto(result);
    }

    @Override
    public AccountDto update(AccountDto dto) {
        return null;
    }

    @Override
    public void delete(AccountDto dto) {}

    @Override
    public AccountDto get(Long id) {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public Collection<AccountDto> getAll() {
        Collection<Account> result = accountRepository.findAll();
        Collection<AccountDto> accounts = new ArrayList<>();

        if (!result.isEmpty()) {
            result.forEach(account -> {
                accounts.add(this.convertToDto(account));
            });
            return accounts;
        }
        throw new AccountNotFoundException();
    }

    @Override
    public AccountDto convertToDto(Account entity) {
        return modelMapper.map(entity, AccountDto.class);
    }

    @Override
    public Account convertToEntity(AccountDto dto) {
        return modelMapper.map(dto, Account.class);
    }

    /**
     * Returns all accounts associated with the given customerId.
     * @param customerId Long
     * @return List<AccountDto>
     */
    public Collection<AccountDto> getAllById(Long customerId) {
        if (customerRepository.findById(customerId).isPresent()) {
            Collection<AccountDto> accounts = new ArrayList<>();
            Customer owner = customerRepository.findById(customerId).get();
            Collection<Account> result = accountRepository.findAllByOwner(owner);

            if (!result.isEmpty()) {
                result.forEach(account -> {
                    // Get transactions for this account and set them to the dto.
                    AccountDto dto = this.convertToDto(account);
                    Collection<TransactionDto> transactions = transactionService.getAllById(dto.getId());
                    dto.setTransactions(transactions);
                    accounts.add(dto);
                });
            }

            return accounts;
        }
        throw new CustomerNotFoundException(customerId);
    }

    /**
     * Instead of adding a custom query to the repository we can also use an ExampleMatcher and a probe
     * to search if something exists within the database.
     *
     * This is just an alternative way of looking for something that exists. In a real world example we would probably
     * add a method to the repository: existsAccountByAccountNumber(String accountNumber);
     *
     * Note: This method will get more expensive when the database starts filling up and the chance increases that
     * there will be a duplicate accountNumber generated. One major improvement would be to keep track of generated
     * accountNumbers each step so we do not start generating numbers we already generated in precious method calls.
     * @return String accountNumber
     */
    public String generateNewAccountNumber() {
        Random value = new Random();
        final String bank = "NL99SPDB";
        int number = value.nextInt(10);
        String accountNumber = bank.concat(String.valueOf(number));

        /*
        Matcher config with the "decription" of the thing we are looking for.
        Ignoring "id" since that is a primary key and by default the matcher looks at the primary key according to
        documentation of the ExampleMatcher class.
         */
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("accountNumber", ignoreCase());

        // Creating probe object and Example object to use for our search.
        AccountDto probe = new AccountDto();
        probe.setAccountNumber(accountNumber);
        Example<Account> example = Example.of(this.convertToEntity(probe));

        if (accountRepository.exists(example)) {
            // The database contains an account with the generated accountNumber.
            return this.generateNewAccountNumber();
        }

        // Unique account number.
        return bank.concat(accountNumber);
    }
}
