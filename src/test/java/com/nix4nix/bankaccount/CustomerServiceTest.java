package com.nix4nix.bankaccount;

import com.nix4nix.bankaccount.controlleradvice.exception.CustomerNotFoundException;
import com.nix4nix.bankaccount.dto.CustomerDto;
import com.nix4nix.bankaccount.entity.Customer;
import com.nix4nix.bankaccount.repository.CustomerRepository;
import com.nix4nix.bankaccount.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        // Arrange test data
        Customer entity = new Customer(999L, "T", "Tester", "", "");
        CustomerDto dto = new CustomerDto();
        dto.setId(999L);
        dto.setName("T");
        dto.setSurname("Tester");
        dto.setEmail("");
        dto.setPhone("");

        CustomerDto expected = new CustomerDto();
        expected.setId(999L);
        expected.setName("T");
        expected.setSurname("Tester");
        expected.setEmail("");
        expected.setPhone("");

        // Define behavior for the mocked ModelMapper
        when(customerService.convertToEntity(dto)).thenReturn(entity);
        when(customerService.convertToDto(entity)).thenReturn(dto);
        when(customerRepository.save(entity)).thenReturn(entity);

        // Act and Assert (AAA)
        CustomerDto result = customerService.create(dto);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getName(), result.getName());
    }

    @Test
    public void testDeleteException() {
        CustomerDto dto = new CustomerDto();
        dto.setId(999L);
        dto.setName("T");
        dto.setSurname("Tester");
        dto.setEmail("");
        dto.setPhone("");

        // Define behavior for the mocked ModelMapper
        when(customerRepository.findById(dto.getId())).thenReturn(Optional.empty());

        try {
            customerService.delete(dto);
            fail("testDeleteException failed. No Exception thrown.");
        } catch (CustomerNotFoundException exception) {
            assertEquals("Could not find customer " + dto.getId(),exception.getMessage());
        }
    }
}
