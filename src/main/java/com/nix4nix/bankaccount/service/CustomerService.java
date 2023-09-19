package com.nix4nix.bankaccount.service;

import com.nix4nix.bankaccount.entity.Customer;
import com.nix4nix.bankaccount.repository.CustomerRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
@AllArgsConstructor
public class CustomerService implements BaseService<Customer> {

    private CustomerRepository customerRepository;

    @Override
    public void create(Customer entity) {
        customerRepository.save(entity);
    }

    @Override
    public void update(Customer entity) {
        if (customerRepository.existsById(entity.getId())) {
            customerRepository.save(entity);
        }
    }

    @Override
    public void delete(Customer entity) {
        if (customerRepository.existsById(entity.getId())) {
            customerRepository.delete(entity);
        }
    }

    @Override
    public Customer get(Long id) {
        //TODO: Should use CustomerDTO, add mapper?

        if (customerRepository.existsById(id)) {
            return customerRepository.findById(id).get();
        }

        return null;
    }

    @Override
    public Collection<Customer> getAll() {
        //TODO: Should use CustomerDTO, add mapper?
        return customerRepository.findAll();
    }
}
