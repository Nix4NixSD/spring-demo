package com.nix4nix.bankaccount;

import com.nix4nix.bankaccount.controlleradvice.exception.AccountMalformedException;
import com.nix4nix.bankaccount.dto.AccountDto;
import com.nix4nix.bankaccount.entity.Account;
import com.nix4nix.bankaccount.entity.Customer;
import com.nix4nix.bankaccount.repository.AccountRepository;
import com.nix4nix.bankaccount.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void accountNumberGeneration() {

        when(accountRepository.exists(any())).thenReturn(false);

        String result = accountService.generateNewAccountNumber();

        assertEquals(18, result.length());
        assertTrue(result.contains("NL99SPDB"));
    }

    @Test
    public void successfulAccountCreation() {
        // Arrange test data and objects
        AccountDto dto = new AccountDto();
        dto.setType(Account.AccountTypes.BETAALREKENING);
        dto.setBalance(BigDecimal.valueOf(350)); // Tree fiddy

        Account entity = new Account("", dto.getBalance(), dto.getType(), LocalDateTime.now(), new Customer());

        // Mock repository behavior and make it return Account entity.
        when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        // Define behavior for the mocked ModelMapper
        when(accountService.convertToEntity(dto)).thenReturn(entity);
        when(accountService.convertToDto(entity)).thenReturn(dto);
        when(accountRepository.save(entity)).thenReturn(entity);

        // Act and Assert
        AccountDto result = accountService.create(dto);

        assertNotNull(result);
        assertEquals(Account.AccountTypes.BETAALREKENING, result.getType());
        assertTrue(result.getBalance().compareTo(BigDecimal.ZERO) >= 0);
        assertNotNull(result.getCreatedAt());
    }

    @Test
    public void invalidAccountType() {
        // Act and Assert
        assertThrows(AccountMalformedException.class, () -> accountService.validateAccountType("Invalid"));
    }

    @Test
    public void validAccountType() {
        // Arrange test data and objects
        String accountType_lowerCase = "betaalrekening";
        String accountType_chaosCased = "BeTaAlReKeNiNG";
        String accountType_upperCase = "BeTaAlReKeNiNG";

        String expected = Account.AccountTypes.BETAALREKENING.name();

        // Act
        Account.AccountTypes type_lowerCase = accountService.validateAccountType(accountType_lowerCase);
        Account.AccountTypes type_chaosCased = accountService.validateAccountType(accountType_lowerCase);
        Account.AccountTypes type_upperCase = accountService.validateAccountType(accountType_lowerCase);

        // Assert
        assertEquals(expected, type_lowerCase.name());
        assertEquals(expected, type_chaosCased.name());
        assertEquals(expected, type_upperCase.name());
    }
}
