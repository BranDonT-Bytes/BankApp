package com.bankapp;

public class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, Customer holder, double balance, double overdraftLimit) {
        super(accountNumber, holder, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) return;
        if (balance - amount < -overdraftLimit) {
            System.out.println("Cannot withdraw: exceeds overdraft limit.");
        } else {
            balance -= amount;
            System.out.println("Withdrawal successful.");
        }
    }

    @Override
    public void printReceipt() {
        System.out.println("[CHECKING ACCOUNT] Account: " + accountNumber + " Holder: " + holder.getUsername() + " Balance: $" + String.format("%.2f", balance));
    }
}
