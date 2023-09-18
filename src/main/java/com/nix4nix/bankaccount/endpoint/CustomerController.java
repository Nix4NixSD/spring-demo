package com.nix4nix.bankaccount.endpoint;

import com.nix4nix.bankaccount.dto.CustomerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
    static final String BASE_URL = "/api/customer";

    public ResponseEntity<CustomerDto> customerDetails() {
        //TODO: Do the necessary steps and return the result
        return new ResponseEntity<>(new CustomerDto(), HttpStatus.OK);
    }
}
