package ma.enset.digitat.banking.services;

import ma.enset.digitat.banking.dtos.BankAccountDTO;
import ma.enset.digitat.banking.dtos.CurrentBankAccountDTO;
import ma.enset.digitat.banking.dtos.CustomerDTO;
import ma.enset.digitat.banking.dtos.SavingBankAccountDTO;
import ma.enset.digitat.banking.entities.BankAccount;
import ma.enset.digitat.banking.entities.CurrentAccount;
import ma.enset.digitat.banking.entities.Customer;
import ma.enset.digitat.banking.entities.SavingAccount;
import ma.enset.digitat.banking.exceptions.BalanceNotSufficientException;
import ma.enset.digitat.banking.exceptions.BankAcountNotFoundException;
import ma.enset.digitat.banking.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CurrentBankAccountDTO saveCurrentBankAccount(Double initialBalance, Double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(Double initialBalance, Double interestRate, Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAcountNotFoundException;
    void debit(String accountId, double amount, String description) throws CustomerNotFoundException, BankAcountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws CustomerNotFoundException, BankAcountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws CustomerNotFoundException, BalanceNotSufficientException, BankAcountNotFoundException;
    List<BankAccountDTO> listBankAccounts();
}
