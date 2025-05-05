package ma.enset.digitat.banking.services;

import ma.enset.digitat.banking.dtos.*;
import ma.enset.digitat.banking.exceptions.BalanceNotSufficientException;
import ma.enset.digitat.banking.exceptions.BankAccountNotFoundException;
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
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws CustomerNotFoundException, BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws CustomerNotFoundException, BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws CustomerNotFoundException, BalanceNotSufficientException, BankAccountNotFoundException;
    List<BankAccountDTO> listBankAccounts();

    List<OperationDTO> accountHistory(String accountId) throws BankAccountNotFoundException;

    AccountHistoryDTO getAccountHistory(String accountID, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);
}
