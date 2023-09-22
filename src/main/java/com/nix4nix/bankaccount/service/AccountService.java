package com.nix4nix.bankaccount.service;

import com.nix4nix.bankaccount.dto.AccountDto;
import com.nix4nix.bankaccount.entity.Account;
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
    public AccountDto create(AccountDto entity) {
        return null;
    }

    @Override
    public AccountDto update(AccountDto entity) {
        return null;
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
