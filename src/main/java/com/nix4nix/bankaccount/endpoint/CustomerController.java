package com.nix4nix.bankaccount.endpoint;

import com.nix4nix.bankaccount.controlleradvice.exception.NotImplementedException;
import com.nix4nix.bankaccount.dto.CustomerDto;
import com.nix4nix.bankaccount.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller implementation.
 * This controller is responsible for making endpoint visible to the client and doing calls to the
 * service that belongs to this controller.
 */
@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
    static final String BASE_URL = "/api/customer";

    /*
    In other classes we use the @AllArgsConstructor annotation which solved the required fields.
    Autowired is a alternative way of getting the required fields for a class implementation.
    Beware of Autowired entanglement though.
     */
    @Autowired
    private CustomerService customerService;

    @GetMapping("/get/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.get(id), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<CustomerDto> getCustomers() {
        throw new NotImplementedException(BASE_URL + "/get");
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto dto) {
        CustomerDto result = customerService.create(dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto dto) {
        CustomerDto result = customerService.update(dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        throw new NotImplementedException(BASE_URL + "/delete/{id}");
    }
}
