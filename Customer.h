#ifndef CUSTOMER_H
#define CUSTOMER_H

#include <string>
#include <vector>

using namespace std;

// forward declare Account (DO NOT include header here)
class Account;

class Customer {
private:
    int id;
    string username;
    string password;

public:
    vector<Account*> accounts;

    Customer(int i, string u, string p) {
        id = i;
        username = u;
        password = p;
    }

    int getId() { return id; }
    string getUsername() { return username; }
    string getPassword() { return password; }
};

#endif