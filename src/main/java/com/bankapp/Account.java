package com.bankapp;

public abstract class Account implements ITransaction {
    protected String accountNumber;
    protected Customer holder;
    protected double balance;

    public Account(String accountNumber, Customer holder, double balance) {
        this.accountNumber = accountNumber;
        this.holder = holder;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public abstract void withdraw(double amount);
    public abstract void printReceipt();
}
