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

    public CustomerEntity createCustomer(CustomerEntity customer) {
        return repo.save(customer);
    }

    public List<CustomerEntity> getCustomers() {
        return repo.findAll();
    }

    public CustomerEntity getCustomerByUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public CustomerEntity updateCustomer(Long id, String username, String password) {
        CustomerEntity existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        existing.setUsername(username);
        existing.setPassword(password);

        return repo.save(existing);
    }

    public void deleteCustomer(Long id) {
        repo.deleteById(id);
    }
}