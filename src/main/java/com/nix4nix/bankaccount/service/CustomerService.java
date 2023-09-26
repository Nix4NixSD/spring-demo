package com.nix4nix.bankaccount.service;

import com.nix4nix.bankaccount.controlleradvice.exception.CustomerNotFoundException;
import com.nix4nix.bankaccount.dto.AccountDto;
import com.nix4nix.bankaccount.dto.CustomerDto;
import com.nix4nix.bankaccount.entity.Customer;
import com.nix4nix.bankaccount.repository.CustomerRepository;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * Customer implementation of BaseService.
 * This service is responsible for customer data operations and business logic.
 */
@Service
@AllArgsConstructor
public class CustomerService implements BaseService<CustomerDto, Customer> {

    /* Note:
    These required fields are injected by the AllArgsConstructor annotation.
    The AllArgsConstructor annotation creates a constructor with all the needed arguments on the fly.
    Using these annotations make the code less cluttered. Alternative to this would be using springs autowired
    annotation but that tend to result into autowired entanglement. Where autowired fields are injected and contain
    themselves autowired fields that are injected... and so on.., which can result for example into fields/properties
    being injected multiple times. Or injections being injected too late, which is a pita to debug.
     */
    private CustomerRepository customerRepository;

    // Using service since this contains the method that will retrieve all accounts for one customer.
    private AccountService accountService;

    private ModelMapper modelMapper;

    /**
     * Adding custom mappers for the dto to entity conversion since we use final fields in our entities.
     * The default mapper used by the modelMapper will use getters and setters to map the data to the other class but
     * since there are no getters and setters for our final fields, that will fail.
     */
    @PostConstruct
    public void configureModelMapper() {
        modelMapper.createTypeMap(CustomerDto.class, Customer.class).setConverter(mappingContext -> {
            CustomerDto dto = mappingContext.getSource();
            return new Customer(
                    dto.getId(),
                    dto.getName(),
                    dto.getSurname(),
                    dto.getEmail(),
                    dto.getPhone()
            );
        });
    }

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
     * Tries to retrieve customer data based on the supplied id.
     * If found, will return CustomerDto with Accounts and Transactions (if any)
     * @param id Long customer id
     * @return customer CustomerDto
     */
    @Override
    public CustomerDto get(Long id) {
        if (customerRepository.findById(id).isPresent()) {
            CustomerDto dto = this.convertToDto(customerRepository.findById(id).get());
            Collection<AccountDto> accounts = accountService.getAllById(dto.getId());
            dto.setAccounts(accounts);
            return dto;
        } else {
            throw new CustomerNotFoundException(id);
        }
    }

    @Override
    public Collection<CustomerDto> getAll() {
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
