#ifndef SAVINGSACCOUNT_H
#define SAVINGSACCOUNT_H

#include "Account.h"

// STEP 4: SavingsAccount inherits from Account
class SavingsAccount : public Account {

public:

    // STEP 4.1: Constructor calls base class constructor
    SavingsAccount(string acc, Customer* h, double b)
        : Account(acc, h, b) {}

    // STEP 4.2: Custom withdrawal rule (POLYMORPHISM)
    void withdraw(double amount) override {

        // Rule: must always keep $100 minimum
        if (balance - amount < 100) {
            cout << "Denied: Minimum balance is $100\n";
            return;
        }

        balance -= amount;
        cout << "Withdrawn: " << amount << endl;
    }

    // STEP 4.3: Custom receipt format
    void printReceipt() override {
        cout << "[SAVINGS ACCOUNT] "
             << accountNumber
             << " Balance: " << balance << endl;
    }
};

#endif