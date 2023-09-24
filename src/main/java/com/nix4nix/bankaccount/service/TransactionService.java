package com.nix4nix.bankaccount.service;

import com.nix4nix.bankaccount.controlleradvice.exception.AccountNotFoundException;
import com.nix4nix.bankaccount.controlleradvice.exception.CustomerNotFoundException;
import com.nix4nix.bankaccount.dto.AccountDto;
import com.nix4nix.bankaccount.dto.TransactionDto;
import com.nix4nix.bankaccount.entity.Account;
import com.nix4nix.bankaccount.entity.Customer;
import com.nix4nix.bankaccount.entity.Transaction;
import com.nix4nix.bankaccount.repository.AccountRepository;
import com.nix4nix.bankaccount.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class TransactionService implements BaseService<TransactionDto, Transaction> {

    private TransactionRepository transactionRepository;

    private AccountRepository accountRepository;

    private ModelMapper modelMapper;

    @Override
    public TransactionDto create(TransactionDto entity) {
        //TODO
        return null;
    }

    @Override
    public TransactionDto update(TransactionDto entity) {
        return null;
    }

    @Override
    public void delete(TransactionDto entity) {}

    @Override
    public TransactionDto get(Long id) {
        return null;
    }

    @Override
    public Collection<TransactionDto> getAll() {
        return null;
    }

    @Override
    public TransactionDto convertToDto(Transaction entity) {
        return modelMapper.map(entity, TransactionDto.class);
    }

    @Override
    public Transaction convertToEntity(TransactionDto dto) {
        return modelMapper.map(dto, Transaction.class);
    }

    /**
     *
     * @param accountId
     * @return
     */
    public Collection<TransactionDto> getAllById(Long accountId) {
        if (accountRepository.findById(accountId).isPresent()) {
            Collection<TransactionDto> transactions = new ArrayList<>();
            Account account = accountRepository.findById(accountId).get();
            Collection<Transaction> result = transactionRepository.findAllByAccount(account);

            if (!result.isEmpty()) {
                result.forEach(transaction -> {
                    transactions.add(this.convertToDto(transaction));
                });
            }

            return transactions;
        }
        throw new AccountNotFoundException(accountId);
    }
}
