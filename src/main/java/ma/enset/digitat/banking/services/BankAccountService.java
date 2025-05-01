package ma.enset.digitat.banking.services;

import ma.enset.digitat.banking.entities.BankAccount;
import ma.enset.digitat.banking.entities.CurrentAccount;
import ma.enset.digitat.banking.entities.Customer;
import ma.enset.digitat.banking.entities.SavingAccount;
import ma.enset.digitat.banking.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    CurrentAccount saveCurrentBankAccount(Double initialBalance, Double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(Double initialBalance, Double interestRate, Long customerId) throws CustomerNotFoundException;
    List<Customer> listCustomers();
    BankAccount getBankAccount(String accountId);
    void debit(String accountId, double amount, String description);
    void credit(String accountId, double amount, String description);
    void transfer(String accountIdSource, String accountIdDestination, double amount);

}
