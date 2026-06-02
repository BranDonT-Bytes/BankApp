package com.bankapp;

public class SavingsAccount extends Account {
    private static final double MIN_BALANCE = 100.0;

    public SavingsAccount(String accountNumber, Customer holder, double balance) {
        super(accountNumber, holder, balance);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) return;
        if (balance - amount < MIN_BALANCE) {
            System.out.println("Cannot withdraw: savings account must maintain $100 minimum balance.");
        } else {
            balance -= amount;
            System.out.println("Withdrawal successful.");
        }
    }

    @Override
    public void printReceipt() {
        System.out.println("[SAVINGS ACCOUNT] Account: " + accountNumber + " Holder: " + holder.getUsername() + " Balance: $" + String.format("%.2f", balance));
    }
}
