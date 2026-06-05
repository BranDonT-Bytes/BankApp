package com.bankapp.integration;

import com.bankapp.entity.CustomerEntity;
import com.bankapp.repository.CustomerRepository;
import com.bankapp.service.BankDataService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankDataService bankDataService;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();
    }

    @AfterEach
    void cleanup() {
        customerRepository.deleteAll();
    }

    @Test
    void login_shouldWork_withRealSpringContext() {

        String username = "john_test_" + System.currentTimeMillis();

        CustomerEntity user =
                new CustomerEntity(username, "1234");

        customerRepository.save(user);

        CustomerEntity result =
                bankDataService.login(username, "1234");

        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    void login_shouldFail_withWrongPassword() {

        String username = "john_test_" + System.currentTimeMillis();

        CustomerEntity user =
                new CustomerEntity(username, "1234");

        customerRepository.save(user);

        assertThrows(RuntimeException.class, () ->
                bankDataService.login(username, "wrong")
        );
    }
}