package com.nix4nix.bankaccount.service;

import com.nix4nix.bankaccount.dto.TransactionDto;
import com.nix4nix.bankaccount.entity.Transaction;
import org.modelmapper.ModelMapper;
import javax.transaction.Transactional;
import java.util.Collection;

@Transactional
public class TransactionService implements BaseService<TransactionDto, Transaction> {

    private ModelMapper modelMapper;

    @Override
    public void create(TransactionDto entity) {

    }

    @Override
    public void update(TransactionDto entity) {

    }

    @Override
    public void delete(TransactionDto entity) {

    }

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
}
