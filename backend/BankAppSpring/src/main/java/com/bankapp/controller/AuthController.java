package com.bankapp.controller;

import com.bankapp.dto.LoginRequest;
import com.bankapp.entity.CustomerEntity;
import com.bankapp.service.BankDataService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final BankDataService service;

    public AuthController(BankDataService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public CustomerEntity login(@RequestBody LoginRequest request) {
        CustomerEntity user = service.login(
            request.getUsername(),
            request.getPassword()
        );

        return user;
    }
}