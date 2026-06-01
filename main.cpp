#include <iostream>
#include <vector>
#include <cctype>
#include <sstream>
#include "Customer.h"
#include "SavingsAccount.h"
#include "CheckingAccount.h"

using namespace std;

/*
====================================================
GLOBAL SYSTEM DATABASE
====================================================
*/
vector<Customer*> customers;

int nextCustomerId = 1;
int nextAccountId = 1;

/*
====================================================
SAFE INPUT VALIDATION FUNCTIONS
====================================================
These prevent crashes from:
- letters in numbers
- special characters
- empty input
*/

/*
Check if string is a valid positive number
(no letters, no special characters, no negatives)
*/
bool isValidNumber(string input) {

    if (input.empty()) return false;

    for (char c : input) {

        if (!isdigit(c) && c != '.') {
            return false;
        }
    }

    return true;
}

/*
Convert string safely to double
*/
double safeDoubleInput() {

    string input;

    while (true) {

        cin >> input;

        if (isValidNumber(input)) {
            return stod(input);
        }

        cout << "Invalid input! Enter a valid number: ";
    }
}

/*
Convert string safely to int
*/
int safeIntInput() {

    string input;

    while (true) {

        cin >> input;

        if (isValidNumber(input)) {
            return stoi(input);
        }

        cout << "Invalid input! Enter a valid option: ";
    }
}

/*
====================================================
LOGIN SEARCH
====================================================
*/
Customer* findCustomer(string username) {

    for (auto c : customers) {
        if (c->getUsername() == username)
            return c;
    }

    return nullptr;
}

/*
====================================================
SEED DATA
====================================================
*/
void seed() {

    Customer* c1 = new Customer(nextCustomerId++, "Brandon", "B123");
    Customer* c2 = new Customer(nextCustomerId++, "Cameron", "C123");

    c1->accounts.push_back(new SavingsAccount(to_string(nextAccountId++), c1, 500));
    c1->accounts.push_back(new CheckingAccount(to_string(nextAccountId++), c1, 200, 300));

    c2->accounts.push_back(new SavingsAccount(to_string(nextAccountId++), c2, 800));
    c2->accounts.push_back(new CheckingAccount(to_string(nextAccountId++), c2, 100, 400));

    customers.push_back(c1);
    customers.push_back(c2);
}

/*
====================================================
VIEW ALL (ADMIN)
====================================================
*/
void viewAll() {

    cout << "\n===== ALL ACCOUNTS =====\n";

    for (auto c : customers) {

        cout << "\nCustomer: " << c->getUsername() << endl;

        cout << "Savings:  " << c->accounts[0]->getBalance() << endl;
        cout << "Checking: " << c->accounts[1]->getBalance() << endl;
    }
}

/*
====================================================
CREATE CUSTOMER (ADMIN)
====================================================
*/
void createCustomer() {

    string username, password;

    cout << "\nEnter username password: ";
    cin >> username >> password;

    cout << "Enter SAVINGS balance: ";
    double savingsBalance = safeDoubleInput();

    cout << "Enter CHECKING balance: ";
    double checkingBalance = safeDoubleInput();

    Customer* c = new Customer(nextCustomerId++, username, password);

    c->accounts.push_back(
        new SavingsAccount(to_string(nextAccountId++), c, savingsBalance)
    );

    c->accounts.push_back(
        new CheckingAccount(to_string(nextAccountId++), c, checkingBalance, 300)
    );

    customers.push_back(c);

    cout << "Customer created!\n";
}

/*
====================================================
DELETE CUSTOMER
====================================================
*/
void deleteCustomer() {

    string username;

    cout << "Enter username: ";
    cin >> username;

    for (int i = 0; i < customers.size(); i++) {

        if (customers[i]->getUsername() == username) {

            delete customers[i];
            customers.erase(customers.begin() + i);

            cout << "Deleted!\n";
            return;
        }
    }

    cout << "Not found!\n";
}

/*
====================================================
CUSTOMER MENU (FULL SAFE VERSION)
====================================================
*/
void customerMenu(Customer* c) {

    while (true) {

        cout << "\n===== CUSTOMER MENU =====\n";
        cout << "1. View Balances\n";
        cout << "2. Deposit\n";
        cout << "3. Withdraw\n";
        cout << "4. Transfer\n";
        cout << "5. Log Out\n";
        cout << "6. Quit Program\n";

        int choice = safeIntInput();

        Account* savings = c->accounts[0];
        Account* checking = c->accounts[1];

        /*
        VIEW BALANCES
        */
        if (choice == 1) {

            cout << "Savings:  " << savings->getBalance() << endl;
            cout << "Checking: " << checking->getBalance() << endl;
        }

        /*
        DEPOSIT (NO NEGATIVE VALUES)
        */
        else if (choice == 2) {

            int type;

            cout << "1. Savings  2. Checking: ";
            type = safeIntInput();

            cout << "Enter amount: ";
            double amount = safeDoubleInput();

            if (amount <= 0) {
                cout << "No negative or zero deposits allowed!\n";
                continue;
            }

            if (type == 1) savings->deposit(amount);
            else checking->deposit(amount);
        }

        /*
        WITHDRAW (NO NEGATIVE VALUES)
        */
        else if (choice == 3) {

            int type;

            cout << "1. Savings  2. Checking: ";
            type = safeIntInput();

            cout << "Enter amount: ";
            double amount = safeDoubleInput();

            if (amount <= 0) {
                cout << "No negative or zero withdrawals allowed!\n";
                continue;
            }

            if (type == 1) savings->withdraw(amount);
            else checking->withdraw(amount);
        }

        /*
        TRANSFER BETWEEN ACCOUNTS
        */
        else if (choice == 4) {

            int direction;

            cout << "1. Savings → Checking\n";
            cout << "2. Checking → Savings\n";
            direction = safeIntInput();

            cout << "Enter amount: ";
            double amount = safeDoubleInput();

            if (amount <= 0) {
                cout << "Invalid transfer amount!\n";
                continue;
            }

            if (direction == 1) {

                if (savings->getBalance() >= amount) {
                    savings->withdraw(amount);
                    checking->deposit(amount);
                    cout << "Transfer successful!\n";
                }
                else cout << "Not enough funds!\n";
            }

            else if (direction == 2) {

                if (checking->getBalance() >= amount) {
                    checking->withdraw(amount);
                    savings->deposit(amount);
                    cout << "Transfer successful!\n";
                }
                else cout << "Not enough funds!\n";
            }
        }

        else if (choice == 5){
            cout << "Logging Out...\n";
            break;
        }

        else if (choice == 6){
            cout << "Ending Program...\n";
            exit(0);
        }
    }
}

/*
====================================================
ADMIN MENU
====================================================
*/
void adminMenu() {

    while (true) {

        cout << "\n===== ADMIN MENU =====\n";
        cout << "1. View All\n";
        cout << "2. Create Customer\n";
        cout << "3. Delete Customer\n";
        cout << "4. Log Out\n";
        cout << "5. Quit\n";

        int choice = safeIntInput();

        if (choice == 1) viewAll();
        else if (choice == 2) createCustomer();
        else if (choice == 3) deleteCustomer();
        else if (choice == 4) {
          cout << "Logging out...\n";
          break;}
        else if (choice == 5) {
          cout << "Ending Program...\n";
          exit(0);}
        else cout << "Invaild Option: Try Again\n";
    }
}

/*
====================================================
LOGIN
====================================================
*/
void login() {

    string u, p;

    cout << "\nLogin Username Password: ";
    cin >> u >> p;

    if (u == "admin" && p == "admin123") {
        adminMenu();
        return;
    }

    for (auto c : customers) {

        if (c->getUsername() == u && c->getPassword() == p) {
            customerMenu(c);
            return;
        }
    }

    cout << "Invalid login!\n";
}

/*
====================================================
MAIN
====================================================
*/
int main() {

    seed();

    while (true) {
        login();
    }

    return 0;
}