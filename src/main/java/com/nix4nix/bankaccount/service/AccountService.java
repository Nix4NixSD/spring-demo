package com.nix4nix.bankaccount.service;

import com.nix4nix.bankaccount.dto.AccountDto;
import com.nix4nix.bankaccount.dto.CustomerDto;
import com.nix4nix.bankaccount.entity.Account;
import com.nix4nix.bankaccount.entity.Customer;
import com.nix4nix.bankaccount.repository.AccountRepository;
import com.nix4nix.bankaccount.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Collection;

@AllArgsConstructor
@Service
public class AccountService implements BaseService<AccountDto, Account> {

    private AccountRepository accountRepository;

    private TransactionRepository transactionRepository;

    private ModelMapper modelMapper;

    @Override
    public void create(AccountDto entity) {

    }

    @Override
    public void update(AccountDto entity) {

    }

    @Override
    public void delete(AccountDto entity) {

    }

    @Override
    public AccountDto get(Long id) {

        return null;
    }

    @Override
    public Collection<AccountDto> getAll() {
        // TODO
        return null;
    }

    @Override
    public AccountDto convertToDto(Account entity) {
        return modelMapper.map(entity, AccountDto.class);
    }

    @Override
    public Account convertToEntity(AccountDto dto) {
        return modelMapper.map(dto, Account.class);
    }
}
