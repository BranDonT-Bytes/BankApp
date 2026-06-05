package com.bankapp.config;

import com.bankapp.entity.CustomerEntity;
import com.bankapp.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepository repo;

    public DataInitializer(CustomerRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {

        if (repo.findByUsername("admin").isEmpty()) {
            CustomerEntity admin = new CustomerEntity();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setCheckingBalance(0.0);
            admin.setSavingsBalance(0.0);

            repo.save(admin);

            System.out.println("✅ Admin account created");
        }
    }
}