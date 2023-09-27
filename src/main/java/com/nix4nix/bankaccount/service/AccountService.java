package com.nix4nix.bankaccount.service;

import com.nix4nix.bankaccount.controlleradvice.exception.AccountMalformedException;
import com.nix4nix.bankaccount.controlleradvice.exception.AccountNotFoundException;
import com.nix4nix.bankaccount.controlleradvice.exception.CustomerNotFoundException;
import com.nix4nix.bankaccount.dto.AccountDto;
import com.nix4nix.bankaccount.dto.TransactionDto;
import com.nix4nix.bankaccount.entity.Account;
import com.nix4nix.bankaccount.entity.Customer;
import com.nix4nix.bankaccount.entity.Transaction;
import com.nix4nix.bankaccount.repository.AccountRepository;
import com.nix4nix.bankaccount.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Random;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@AllArgsConstructor
@Service
public class AccountService implements BaseService<AccountDto, Account> {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    private TransactionService transactionService;

    private ModelMapper modelMapper;

    /**
     * Adding custom mappers for the dto to entity conversion since we use final fields in our entities.
     * The default mapper used by the modelMapper will use getters and setters to map the data to the other class but
     * since there are no getters and setters for our final fields, that will fail.
     */
    @PostConstruct
    public void configureModelMapper() {
        modelMapper.createTypeMap(AccountDto.class, Account.class).setConverter(mappingContext -> {
            System.out.println("Custom Converter Called");
            AccountDto dto = mappingContext.getSource();

            if (customerRepository.findById(dto.getOwnerId()).isEmpty()) {
                throw new CustomerNotFoundException(dto.getOwnerId());
            }

            Customer owner = customerRepository.findById(dto.getOwnerId()).get();
            return new Account(
                    dto.getAccountNumber(),
                    dto.getBalance(),
                    dto.getType(),
                    dto.getCreatedAt(),
                    owner
            );
        });
    }

    /**
     * Customer opens new account.
     * @param dto AccountDto
     * @return AccountDto result
     */
    @Override
    public AccountDto create(AccountDto dto) {
        dto.setAccountNumber(this.generateNewAccountNumber());

        boolean existsAsEnum = EnumSet.allOf(Account.AccountTypes.class)
                .stream().anyMatch(value -> value.name().equals(dto.getType().name()));
        if (!existsAsEnum) {
            throw new AccountMalformedException(dto, "Invalid AccountType given.");
        }

        dto.setBalance(dto.getBalance());
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

    /**
     *
     * @param id Long accountId
     * @return null||AccountDto
     */
    @Override
    public AccountDto get(Long id) {
        if (accountRepository.findById(id).isPresent()) {
            Account account = accountRepository.findById(id).get();
            Collection<TransactionDto> transactions = transactionService.getAllById(account.getId());
            AccountDto dto = this.convertToDto(account);
            dto.setTransactions(transactions);
            return dto;
        }

        return null;
    }

    /**
     * Returns all accounts
     * @return
     */
    @Override
    public Collection<AccountDto> getAll() {
        Collection<Account> result = accountRepository.findAll();
        Collection<AccountDto> accounts = new ArrayList<>();

        if (!result.isEmpty()) {
            result.forEach(account -> {
                Collection<TransactionDto> transactions = transactionService.getAllById(account.getId());
                AccountDto dto = this.convertToDto(account);
                dto.setTransactions(transactions);

                accounts.add(dto);
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
        String accountNumber = "";

        for (int i = 0; i < 10; i++) {
            int number = value.nextInt(10);
            accountNumber = accountNumber.concat(String.valueOf(number));
        }
        accountNumber = bank.concat(accountNumber);

        /*
        Matcher config with the "decription" of the thing we are looking for.
        Ignoring "id" since that is a primary key and by default the matcher looks at the primary key according to
        documentation of the ExampleMatcher class.
         */
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnoreNullValues()
                .withMatcher("accountNumber", exact());

        // Creating probe object and Example object to use for our search.
        Account probe2 = new Account(accountNumber, null, null, null, null);
        Example<Account> example = Example.of(probe2, matcher);

        if (accountRepository.exists(example)) {
            // The database contains an account with the generated accountNumber.
            return this.generateNewAccountNumber();
        }

        // Unique account number.
        return accountNumber;
    }

    /**
     * Checks if the given account type exists as enum in the Account.AccountTypes enum.
     * Returns the correct enum value if the given string exists in the enum.
     * This method will use String.toUpperCade(); to make sure all given Strings are uppercase, since
     * all the enums are also uppercased.
     * @param accountType String accountType
     * @return Account.AccountTypes
     */
    public Account.AccountTypes ValidateAccountType(String accountType) {
        // With the use of EnumSet we can stream the whole enum and check if the given value is in the set.
        String typeUc = accountType.toUpperCase();
        boolean existsAsEnum = EnumSet.allOf(Account.AccountTypes.class)
                .stream().anyMatch(value -> value.name().equals(typeUc));

        if (!existsAsEnum) {
            throw new AccountMalformedException("Invalid account type ".concat(accountType));
        }

        // Return the Enum datatype since that is expected.
        return Account.AccountTypes.valueOf(typeUc);
    }
}
