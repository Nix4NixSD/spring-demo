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

/**
 * Customer implementation of BaseService.
 * This service is responsible for customer data operations and business logic.
 */
@Service
@AllArgsConstructor
public class CustomerService implements BaseService<CustomerDto, Customer> {

    /*
    These required fields are injected by the AllArgsConstructor annotation.
    The AllArgsConstructor annotation creates a constructor with all the needed arguments on the fly.
    Using these annotations make the code less cluttered. Alternative to this would be using springs autowired
    annotation but that tend to result into autowired entanglement. Where autowired fields are injected and contain
    themselves autowired fields that are injected... and so on.., which can result for example into fields/properties
    being injected multiple times. Or injections being injected too late, which is a pita to debug.
     */
    private CustomerRepository customerRepository;

    private AccountRepository accountRepository;

    private ModelMapper modelMapper;

    /**
     * Converts incoming dto to a database entity. Which we can save using the repository.
     * The repository will return the entity whenever the save was successful
     * @param dto CustomerDto
     * @return CustomerDto result
     */
    @Override
    public CustomerDto create(CustomerDto dto) {
        // TODO: -Optional- Check if customer already exists
        Customer entity = this.convertToEntity(dto);
        Customer result = customerRepository.save(entity);
        return this.convertToDto(result);
    }

    /**
     * Converts incoming dto to a database entity. Which we can save using the repository.
     * The repository will return the entity whenever the save was successful
     * @param dto CustomerDto
     * @return CustomerDto result
     */
    @Override
    public CustomerDto update(CustomerDto dto) {
        if (customerRepository.existsById(dto.getId())) {
            Customer entity = this.convertToEntity(dto);
            Customer result = customerRepository.save(entity);
            return this.convertToDto(result);
        } else {
            throw new CustomerNotFoundException(dto.getId());
        }
    }

    /**
     * Tries to delete the incoming dto if the entity exists.
     * @param dto CustomerDto
     */
    @Override
    public void delete(CustomerDto dto) {
        if (customerRepository.existsById(dto.getId())) {
            Customer entity = this.convertToEntity(dto);
            customerRepository.delete(entity);
        } else {
            throw new CustomerNotFoundException(dto.getId());
        }
    }

    /**
     * TODO DOC
     * @param id
     * @return
     */
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

    /**
     * Optional. Will implement this if there is time to spare.
     * @return
     */
    @Override
    public Collection<CustomerDto> getAll() {
        //TODO: Should use CustomerDTO, add mapper?
        // get all entity's from database and stream collection through the mapper.
        return null;
    }

    /**
     * Default conversion method that will convert the given entity to the corresponding dto class.
     * This method uses the globally available ModelMapper.
     * @param entity Customer
     * @return dto CustomerDto
     */
    @Override
    public CustomerDto convertToDto(Customer entity) {
        return modelMapper.map(entity, CustomerDto.class);
    }

    /**
     * Default conversion method that will convert the given dto class to the corresponding entity class.
     * This method uses the globally available ModelMapper.
     * @param dto CustomerDto
     * @return entity Customer
     */
    @Override
    public Customer convertToEntity(CustomerDto dto) {
        return modelMapper.map(dto, Customer.class);
    }
}
