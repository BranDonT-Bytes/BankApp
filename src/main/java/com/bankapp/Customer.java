package com.bankapp;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int id;
    private String username;
    private String password;
    private int nextAccountNumber = 1;
    public List<Account> accounts = new ArrayList<>();

    public Customer(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String nextAccountNumber() {
        return String.valueOf(nextAccountNumber++);
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void addAccount(Account a) { accounts.add(a); }
}
