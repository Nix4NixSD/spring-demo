package com.nix4nix.bankaccount.service;

import com.nix4nix.bankaccount.controlleradvice.exception.AccountNotFoundException;
import com.nix4nix.bankaccount.controlleradvice.exception.CustomerNotFoundException;
import com.nix4nix.bankaccount.dto.AccountDto;
import com.nix4nix.bankaccount.entity.Account;
import com.nix4nix.bankaccount.entity.Customer;
import com.nix4nix.bankaccount.repository.AccountRepository;
import com.nix4nix.bankaccount.repository.CustomerRepository;
import com.nix4nix.bankaccount.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@Service
public class AccountService implements BaseService<AccountDto, Account> {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    private TransactionRepository transactionRepository;

    private ModelMapper modelMapper;

    @Override
    public AccountDto create(AccountDto entity) {
        return null;
    }

    @Override
    public AccountDto update(AccountDto entity) {
        return null;
    }

    @Override
    public void delete(AccountDto entity) {}

    @Override
    public AccountDto get(Long id) {
        return null;
    }

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
                    accounts.add(this.convertToDto(account));
                });
            }

            return accounts;
        }
        throw new CustomerNotFoundException(customerId);
    }
}
