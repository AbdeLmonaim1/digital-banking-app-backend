package ma.enset.digitat.banking.services;

import ma.enset.digitat.banking.entities.BankAccount;
import ma.enset.digitat.banking.entities.Customer;

import java.util.List;

public class BankAccountServiceImpl implements BankAccountService {
    @Override
    public Customer saveCustomer(Customer customer) {
        return null;
    }

    @Override
    public BankAccount saveBankAccount(BankAccount bankAccount) {
        return null;
    }

    @Override
    public List<Customer> listCustomers() {
        return List.of();
    }

    @Override
    public BankAccount getBankAccount(String accountId) {
        return null;
    }

    @Override
    public void debit(String accountId, double amount, String description) {

    }

    @Override
    public void credit(String accountId, double amount, String description) {

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) {

    }
}
