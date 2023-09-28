package com.nix4nix.bankaccount.service;

import com.nix4nix.bankaccount.controlleradvice.exception.AccountNotFoundException;
import com.nix4nix.bankaccount.dto.TransactionDto;
import com.nix4nix.bankaccount.entity.Account;
import com.nix4nix.bankaccount.entity.Transaction;
import com.nix4nix.bankaccount.repository.AccountRepository;
import com.nix4nix.bankaccount.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TransactionService implements BaseService<TransactionDto, Transaction> {

    private TransactionRepository transactionRepository;

    private AccountRepository accountRepository;

    private ModelMapper modelMapper;

    /**
     * Adding custom mappers for the dto to entity conversion since we use final fields in our entities.
     * The default mapper used by the modelMapper will use getters and setters to map the data to the other class but
     * since there are no getters and setters for our final fields, that will fail.
     */
    @PostConstruct
    public void configureModelMapper() {
        modelMapper.createTypeMap(TransactionDto.class, Transaction.class).setConverter(mappingContext -> {
            TransactionDto dto = mappingContext.getSource();

            if (accountRepository.findById(dto.getAccountId()).isEmpty()) {
                throw new AccountNotFoundException(dto.getAccountId());
            }

            Account account = accountRepository.findById(dto.getAccountId()).get();
            return new Transaction(
                    dto.getType(),
                    dto.getAmount(),
                    dto.getBalance(),
                    dto.getCreatedAt(),
                    dto.getDescription(),
                    account
            );
        });
    }

    // Default config for Transactional, this is how the Transactional is configured.
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 60, readOnly = false, rollbackFor = Exception.class)
    @Override
    public TransactionDto create(TransactionDto dto) {
        Transaction entity = this.convertToEntity(dto);
        Transaction result = transactionRepository.save(entity);
        return this.convertToDto(result);
    }

    @Override
    public TransactionDto update(TransactionDto dto) {
        return null;
    }

    @Override
    public void delete(TransactionDto dto) {}

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
