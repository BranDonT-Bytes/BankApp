#ifndef CHECKINGACCOUNT_H
#define CHECKINGACCOUNT_H

#include "Account.h"

// STEP 5: Checking account behaves differently from savings
class CheckingAccount : public Account {

private:
    double overdraftLimit;

public:

    // STEP 5.1: Constructor includes overdraft rule
    CheckingAccount(string acc, Customer* h, double b, double limit)
        : Account(acc, h, b) {

        overdraftLimit = limit;
    }

    // STEP 5.2: Withdrawal allows negative balance (within limit)
    void withdraw(double amount) override {

        if (balance - amount < -overdraftLimit) {
            cout << "Denied: overdraft limit exceeded\n";
            return;
        }

        balance -= amount;
        cout << "Withdrawn: " << amount << endl;
    }

    // STEP 5.3: Receipt output
    void printReceipt() override {
        cout << "[CHECKING ACCOUNT] "
             << accountNumber
             << " Balance: " << balance << endl;
    }
};

#endif