package com.nix4nix.bankaccount.utility;

import com.nix4nix.bankaccount.entity.Account;
import com.nix4nix.bankaccount.entity.Customer;
import com.nix4nix.bankaccount.entity.Transaction;
import com.nix4nix.bankaccount.repository.AccountRepository;
import com.nix4nix.bankaccount.repository.CustomerRepository;
import com.nix4nix.bankaccount.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This class hooks into the Springboot boot routine and will be ran whenever the Springboot application is started.
 */
@Component
public class StartUpRunner implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public void run(String... args) throws Exception {
        addCustomerData();
        addAccountData();
    }

    //Adds the customer data using the CustomerRepository
    private void addCustomerData() {
        customerRepository.save(new Customer(null, "Jan", "Jansma", "jansma@xyz.nl", "0611111111"));
        customerRepository.save(new Customer(null, "John", "Doe", "j.doe@xyz.nl", "0622222222"));
        customerRepository.save(new Customer(null, "Erika", "Doe", "e.doe@xyz.nl", "0633333333"));
    }

    //Adds the account data using the AccountRepository
    private void addAccountData() {
        BigDecimal balance = new BigDecimal("1500.05");
        Customer owner = customerRepository.findById(1L).get(); // getById() is deprecated and this is the (not deprecated) workaround.
        accountRepository.save(new Account("NL99SPDB0123456789", balance, Account.AccountTypes.BETAALREKENING, LocalDateTime.now(), owner));

        balance = new BigDecimal("2345.65");
        owner = customerRepository.findById(2L).get();
        accountRepository.save(new Account("NL99SPDB0987654321", balance, Account.AccountTypes.BETAALREKENING, LocalDateTime.now(), owner));

        balance = new BigDecimal("123.23");
        owner = customerRepository.findById(3L).get();
        accountRepository.save(new Account("NL99SPDB0987654321", balance, Account.AccountTypes.BETAALREKENING, LocalDateTime.now(), owner));
    }

    //Adds the transaction data using the TransactionRepository
    private void addTransactionData() {
        //TODO: Add some transaction test data
    }
}
