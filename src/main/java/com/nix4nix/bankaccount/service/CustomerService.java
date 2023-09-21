package com.nix4nix.bankaccount.service;

import com.nix4nix.bankaccount.controlleradvice.exception.CustomerNotFoundException;
import com.nix4nix.bankaccount.dto.CustomerDto;
import com.nix4nix.bankaccount.entity.Customer;
import com.nix4nix.bankaccount.repository.AccountRepository;
import com.nix4nix.bankaccount.repository.CustomerRepository;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
@AllArgsConstructor
public class CustomerService implements BaseService<CustomerDto, Customer> {

    private CustomerRepository customerRepository;

    private AccountRepository accountRepository;

    private ModelMapper modelMapper;

    @Override
    public void create(CustomerDto dto) {
        // TODO: Check if customer already exists
//        if (customerRepository.existsById(dto.getId())) {
            Customer entity = this.convertToEntity(dto);
            customerRepository.save(entity);
//        }
    }

    @Override
    public void update(CustomerDto dto) {
        if (customerRepository.existsById(dto.getId())) {
            Customer entity = this.convertToEntity(dto);
            customerRepository.save(entity);
        } else {
            throw new CustomerNotFoundException(dto.getId());
        }
    }

    @Override
    public void delete(CustomerDto dto) {
        if (customerRepository.existsById(dto.getId())) {
            Customer entity = this.convertToEntity(dto);
            customerRepository.delete(entity);
        } else {
            throw new CustomerNotFoundException(dto.getId());
        }
    }

    @Override
    public CustomerDto get(Long id) {
        if (customerRepository.findById(id).isPresent()) {
            // TODO: Get accountDto(s) from Account service and put account info into customerDto.
            CustomerDto dto = this.convertToDto(customerRepository.findById(id).get());
            return dto;
        } else {
            throw new CustomerNotFoundException(id);
        }
    }

    @Override
    public Collection<CustomerDto> getAll() {
        //TODO: Should use CustomerDTO, add mapper?
        // get all entity's from database and stream collection through the mapper.


        return null;
    }

    @Override
    public CustomerDto convertToDto(Customer entity) {
        return modelMapper.map(entity, CustomerDto.class);
    }

    @Override
    public Customer convertToEntity(CustomerDto dto) {
        return modelMapper.map(dto, Customer.class);
    }
}
