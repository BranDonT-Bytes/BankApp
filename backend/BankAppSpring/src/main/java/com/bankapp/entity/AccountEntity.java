package com.bankapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name="accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountType;

    private Double balance;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private CustomerEntity customer;

    public AccountEntity() {}

    public Long getId() { return id; }

    public String getAccountType() { return accountType; }

    public Double getBalance() { return balance; }

    public CustomerEntity getCustomer() { return customer; }

    public void setId(Long id) { this.id = id; }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
}