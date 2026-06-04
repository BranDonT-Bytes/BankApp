package com.bankapp.controller;

import com.bankapp.entity.CustomerEntity;
import com.bankapp.service.BankDataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class BankController {

    private final BankDataService service;

    public BankController(BankDataService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public CustomerEntity create(@RequestBody CustomerEntity customer) {
    return service.createCustomer(customer);
    }

    // READ ALL
    @GetMapping
    public List<CustomerEntity> getAll() {
        return service.getCustomers();
    }

    // READ ONE
    @GetMapping("/{username}")
    public CustomerEntity getOne(@PathVariable String username) {
        return service.getCustomerByUsername(username);
    }

    // UPDATE
    @PutMapping("/{id}")
    public CustomerEntity update(
            @PathVariable Long id,
            @RequestBody CustomerEntity customer
    ) {
        return service.updateCustomer(id, customer.getUsername(), customer.getPassword());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteCustomer(id);
    }
}