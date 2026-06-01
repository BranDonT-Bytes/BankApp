#ifndef ITRANSACTION_H
#define ITRANSACTION_H

// STEP 1: Interface (pure abstract class in C++)
// Purpose: forces all accounts to implement printReceipt()
class ITransaction {
public:
    // PURE VIRTUAL FUNCTION
    // = must be overridden in derived classes
    virtual void printReceipt() = 0;

    // Virtual destructor (good practice for inheritance)
    virtual ~ITransaction() {}
};

#endif