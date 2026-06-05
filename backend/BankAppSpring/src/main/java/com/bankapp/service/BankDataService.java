package com.bankapp.service;

import com.bankapp.entity.CustomerEntity;
import com.bankapp.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankDataService {

    private final CustomerRepository repo;

    public BankDataService(CustomerRepository repo) {
        this.repo = repo;
    }

    public CustomerEntity login(String username, String password) {
        CustomerEntity customer = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!customer.getPassword().equals(password)) {
            throw new RuntimeException("Invalid username or password");
        }

        return customer;
    }

    public List<CustomerEntity> getCustomers() {
        return repo.findAll();
    }

    public CustomerEntity getCustomerByUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public CustomerEntity depositChecking(String username, Double amount) {
        CustomerEntity customer = getCustomerByUsername(username);
        customer.setCheckingBalance(customer.getCheckingBalance() + amount);
        return repo.save(customer);
    }

    public CustomerEntity withdrawChecking(String username, Double amount) {
        CustomerEntity customer = getCustomerByUsername(username);

        if (customer.getCheckingBalance() - amount < -100) {
            throw new RuntimeException("Checking overdraft limit exceeded (-100)");
        }

        customer.setCheckingBalance(customer.getCheckingBalance() - amount);
        return repo.save(customer);
    }

    public CustomerEntity depositSavings(String username, Double amount) {
        CustomerEntity customer = getCustomerByUsername(username);
        customer.setSavingsBalance(customer.getSavingsBalance() + amount);
        return repo.save(customer);
    }

    public CustomerEntity withdrawSavings(String username, Double amount) {
        if (amount > 500) {
            throw new RuntimeException("Savings withdrawal limit is $500");
        }

        CustomerEntity customer = getCustomerByUsername(username);

        if (customer.getSavingsBalance() < amount) {
            throw new RuntimeException("Insufficient savings funds");
        }

        customer.setSavingsBalance(customer.getSavingsBalance() - amount);
        return repo.save(customer);
    }

    public CustomerEntity transferCheckingToSavings(String username, Double amount) {
        CustomerEntity customer = getCustomerByUsername(username);

        if (customer.getCheckingBalance() - amount < -100) {
            throw new RuntimeException("Checking overdraft limit exceeded (-100)");
        }

        customer.setCheckingBalance(customer.getCheckingBalance() - amount);
        customer.setSavingsBalance(customer.getSavingsBalance() + amount);

        return repo.save(customer);
    }

    public CustomerEntity transferSavingsToChecking(String username, Double amount) {
        CustomerEntity customer = getCustomerByUsername(username);

        if (customer.getSavingsBalance() < amount) {
            throw new RuntimeException("Insufficient savings funds");
        }

        customer.setSavingsBalance(customer.getSavingsBalance() - amount);
        customer.setCheckingBalance(customer.getCheckingBalance() + amount);

        return repo.save(customer);
    }
}