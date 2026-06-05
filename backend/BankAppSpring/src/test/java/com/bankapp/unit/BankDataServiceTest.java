package com.bankapp.unit;

import com.bankapp.entity.CustomerEntity;
import com.bankapp.repository.CustomerRepository;
import com.bankapp.service.BankDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankDataServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private BankDataService bankDataService;

    @Test
    void login_shouldReturnUser_whenCredentialsAreCorrect() {

        CustomerEntity mockUser = new CustomerEntity("john", "1234");

        when(customerRepository.findByUsername("john"))
                .thenReturn(Optional.of(mockUser));

        CustomerEntity result = bankDataService.login("john", "1234");

        assertNotNull(result);
        assertEquals("john", result.getUsername());
    }

    @Test
    void login_shouldThrowException_whenUserNotFound() {

        when(customerRepository.findByUsername("john"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                bankDataService.login("john", "1234")
        );
    }

    @Test
    void login_shouldThrowException_whenPasswordIncorrect() {

        CustomerEntity mockUser = new CustomerEntity("john", "1234");

        when(customerRepository.findByUsername("john"))
                .thenReturn(Optional.of(mockUser));

        assertThrows(RuntimeException.class, () ->
                bankDataService.login("john", "wrong")
        );
    }
}