package com.bankapp.controller;

import com.bankapp.dto.TransactionRequest;
import com.bankapp.dto.TransferRequest;
import com.bankapp.entity.CustomerEntity;
import com.bankapp.service.BankDataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:5173")
public class BankController {

    private final BankDataService service;

    public BankController(BankDataService service) {
        this.service = service;
    }

    @GetMapping
    public List<CustomerEntity> getAll() {
        return service.getCustomers();
    }

    @GetMapping("/{username}")
    public CustomerEntity getByUsername(@PathVariable String username) {
        return service.getCustomerByUsername(username);
    }

    @PostMapping
    public CustomerEntity createCustomer(@RequestBody CustomerEntity customer) {
        return service.createCustomer(customer);
    }

    @DeleteMapping("/{username}")
    public void deleteCustomer(@PathVariable String username) {
        service.deleteCustomer(username);
    }

    @PostMapping("/checking/deposit")
    public CustomerEntity depositChecking(@RequestBody TransactionRequest request) {
        return service.depositChecking(request.getUsername(), request.getAmount());
    }

    @PostMapping("/checking/withdraw")
    public CustomerEntity withdrawChecking(@RequestBody TransactionRequest request) {
        return service.withdrawChecking(request.getUsername(), request.getAmount());
    }

    @PostMapping("/savings/deposit")
    public CustomerEntity depositSavings(@RequestBody TransactionRequest request) {
        return service.depositSavings(request.getUsername(), request.getAmount());
    }

    @PostMapping("/savings/withdraw")
    public CustomerEntity withdrawSavings(@RequestBody TransactionRequest request) {
        return service.withdrawSavings(request.getUsername(), request.getAmount());
    }

    @PostMapping("/transfer/checking-to-savings")
    public CustomerEntity transferCheckingToSavings(@RequestBody TransactionRequest request) {
        return service.transferCheckingToSavings(request.getUsername(), request.getAmount());
    }

    @PostMapping("/transfer/savings-to-checking")
    public CustomerEntity transferSavingsToChecking(@RequestBody TransactionRequest request) {
        return service.transferSavingsToChecking(request.getUsername(), request.getAmount());
    }
}