package com.bankapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Customer> customers = new ArrayList<>();
    private static int nextCustomerId = 1;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seed();
        while (true) {
            System.out.println("\n--- BankApp Login ---");
            System.out.print("Username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            if (username.equals("admin") && password.equals("admin123")) {
                adminMenu();
            } else {
                Customer c = findCustomer(username);
                if (c != null && c.getPassword().equals(password)) {
                    customerMenu(c);
                } else {
                    System.out.println("Invalid credentials.");
                }
            }
        }
    }

    private static void seed() {
        Customer b = new Customer(nextCustomerId++, "Brandon", "B123");
        Customer c = new Customer(nextCustomerId++, "Cameron", "C123");

        SavingsAccount s1 = new SavingsAccount(b.nextAccountNumber(), b, 500);
        CheckingAccount ca1 = new CheckingAccount(b.nextAccountNumber(), b, 200, 100);
        b.addAccount(s1);
        b.addAccount(ca1);

        SavingsAccount s2 = new SavingsAccount(c.nextAccountNumber(), c, 150);
        c.addAccount(s2);
        CheckingAccount ca2 = new CheckingAccount(c.nextAccountNumber(), c, 500, 200);
        c.addAccount(ca2);

        customers.add(b);
        customers.add(c);
    }

    private static Customer findCustomer(String username) {
        for (Customer c : customers) if (c.getUsername().equals(username)) return c;
        return null;
    }

    private static Customer findCustomerById(int id) {
        for (Customer c : customers) if (c.getId() == id) return c;
        return null;
    }

     private static void createAccountForCustomer() {
        System.out.print("Customer ID: ");
        int id = safeIntInput();
        Customer c = findCustomerById(id);
        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("1) Savings");
        System.out.println("2) Checking");
        System.out.print("Account type: ");
        String type = scanner.nextLine().trim();

        if (type.equals("1")) {
            System.out.print("Initial balance (min 100): ");
            double balance = safeDoubleInput();
            if (balance < 100) {
                System.out.println("Savings account requires at least $100.");
                return;
            }
            SavingsAccount account = new SavingsAccount(c.nextAccountNumber(), c, balance);
            c.addAccount(account);
            System.out.println("Savings account created for " + c.getUsername() + ".");
        } else if (type.equals("2")) {
            System.out.print("Initial balance: ");
            double balance = safeDoubleInput();
            System.out.print("Overdraft limit: ");
            double overdraft = safeDoubleInput();
            CheckingAccount account = new CheckingAccount(c.nextAccountNumber(), c, balance, overdraft);
            c.addAccount(account);
            System.out.println("Checking account created for " + c.getUsername() + ".");
        } else {
            System.out.println("Invalid account type.");
        }
    }

    private static void deleteCustomer() {
        System.out.print("Customer ID: ");
        int id = safeIntInput();
        Customer c = findCustomerById(id);
        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.print("Type YES to confirm deletion of " + c.getUsername() + ": ");
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("YES")) {
            customers.remove(c);
            System.out.println("Customer deleted.");
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1) List customers");
            System.out.println("2) Create customer");
            System.out.println("3) Delete customer");
            System.out.println("4) Logout");
            System.out.println("5) Exit");
            System.out.print("Choice: ");
            String ch = scanner.nextLine().trim();
            if (ch.equals("1")) {
                for (Customer c : customers) {
                    System.out.println("ID: " + c.getId() + " User: " + c.getUsername());
                    for (Account a : c.accounts) a.printReceipt();
                }
            } else if (ch.equals("2")) {
                System.out.print("New username: ");
                String u = scanner.nextLine().trim();
                System.out.print("Password: ");
                String p = scanner.nextLine().trim();
                System.out.print("Account 1 (Savings) initial balance: ");
                double balance1 = safeDoubleInput();
                System.out.print("Account 2 (Checking) initial balance: ");
                double balance2 = safeDoubleInput();
                
                Customer nc = new Customer(nextCustomerId++, u, p);
                SavingsAccount sa = new SavingsAccount(nc.nextAccountNumber(), nc, balance1);
                CheckingAccount ca = new CheckingAccount(nc.nextAccountNumber(), nc, balance2, 500);
                nc.addAccount(sa);
                nc.addAccount(ca);
                customers.add(nc);
                System.out.println("Customer created with 2 accounts (Savings and Checking with $500 overdraft limit).");
            } else if (ch.equals("3")) {
                deleteCustomer();
            } else if (ch.equals("4")) {
                System.out.println("Logging out...");
                break;
            } else if (ch.equals("5")){
                System.exit(0);
            }
        }
    }

    private static void customerMenu(Customer c) {
        while (true) {
            System.out.println("\n--- Customer Menu (" + c.getUsername() + ") ---");
            System.out.println("1) List accounts");
            System.out.println("2) Deposit");
            System.out.println("3) Withdraw");
            System.out.println("4) Transfer");
            System.out.println("5) Logout");
            System.out.println("6) Exit");
            System.out.print("Choice: ");
            String ch = scanner.nextLine().trim();
            if (ch.equals("1")) {
                for (Account a : c.accounts) a.printReceipt();
            } else if (ch.equals("2")) {
                Account a = chooseAccount(c);
                if (a != null) {
                    System.out.print("Amount: ");
                    double amt = safeDoubleInput();
                    a.deposit(amt);
                    System.out.println("Deposit complete.");
                }
            } else if (ch.equals("3")) {
                Account a = chooseAccount(c);
                if (a != null) {
                    System.out.print("Amount: ");
                    double amt = safeDoubleInput();
                    a.withdraw(amt);
                }
            } else if (ch.equals("4")) {
                Account from = chooseAccount(c);
                if (from == null) continue;
                System.out.print("Target username: ");
                String target = scanner.nextLine().trim();
                Customer tc = findCustomer(target);
                if (tc == null) { System.out.println("Target not found."); continue; }
                Account to = chooseAccount(tc);
                if (to == null) continue;
                System.out.print("Amount: ");
                double amt = safeDoubleInput();
                double before = from.getBalance();
                from.withdraw(amt);
                if (from.getBalance() != before) {
                    to.deposit(amt);
                    System.out.println("Transfer complete.");
                } else {
                    System.out.println("Transfer failed.");
                }
            } else if (ch.equals("5")) {
                System.out.println("Logging out...");
                break;
            }
            else if (ch.equals("6")) {
                System.out.println("Exiting...");
                System.exit(0);
            }
        }
    }

    private static Account chooseAccount(Customer c) {
        if (c.accounts.isEmpty()) { System.out.println("No accounts."); return null; }
        for (int i = 0; i < c.accounts.size(); i++) {
            Account a = c.accounts.get(i);
            System.out.println((i+1) + ") " + a.getAccountNumber() + " - $" + String.format("%.2f", a.getBalance()));
        }
        System.out.print("Choose account: ");
        int idx = safeIntInput();
        if (idx < 1 || idx > c.accounts.size()) { System.out.println("Invalid choice."); return null; }
        return c.accounts.get(idx-1);
    }

    private static double safeDoubleInput() {
        while (true) {
            String s = scanner.nextLine().trim();
            try { return Double.parseDouble(s); } catch (Exception e) { System.out.print("Enter a valid number: "); }
        }
    }

    private static int safeIntInput() {
        while (true) {
            String s = scanner.nextLine().trim();
            try { return Integer.parseInt(s); } catch (Exception e) { System.out.print("Enter a valid integer: "); }
        }
    }
}
