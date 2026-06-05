package com.bankapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customers", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "CUSTOMER";

    @Column(nullable = true)
    private Double checkingBalance;

    @Column(nullable = true)
    private Double savingsBalance;

    public CustomerEntity() {}

    public CustomerEntity(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;

        if (!"ADMIN".equals(role)) {
            this.checkingBalance = 0.0;
            this.savingsBalance = 0.0;
        } else {
            this.checkingBalance = null;
            this.savingsBalance = null;
        }
    }

    public CustomerEntity(String username, String password) {
        this(username, password, "CUSTOMER");
    }

    // getters/setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public Double getCheckingBalance() { return checkingBalance; }
    public Double getSavingsBalance() { return savingsBalance; }

    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setCheckingBalance(Double checkingBalance) { this.checkingBalance = checkingBalance; }
    public void setSavingsBalance(Double savingsBalance) { this.savingsBalance = savingsBalance; }
}