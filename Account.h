#ifndef ACCOUNT_H
#define ACCOUNT_H

#include <iostream>
#include "ITransaction.h"

using namespace std;

// IMPORTANT FIX:
// We only declare Customer exists (no include)
class Customer;

class Account : public ITransaction {

protected:
    string accountNumber;
    Customer* holder;   // pointer works with forward declaration
    double balance;

public:

    Account(string acc, Customer* h, double b) {
        accountNumber = acc;
        holder = h;
        balance = b;
    }

    string getAccountNumber() { return accountNumber; }
    double getBalance() { return balance; }

    virtual void deposit(double amount) {
        balance += amount;
    }

    virtual void withdraw(double amount) = 0;
    virtual void printReceipt() = 0;
};

#endif