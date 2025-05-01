package ma.enset.digitat.banking.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.digitat.banking.entities.BankAccount;
import ma.enset.digitat.banking.entities.CurrentAccount;
import ma.enset.digitat.banking.entities.Customer;
import ma.enset.digitat.banking.entities.SavingAccount;
import ma.enset.digitat.banking.enums.AccountStatus;
import ma.enset.digitat.banking.exceptions.CustomerNotFoundException;
import ma.enset.digitat.banking.repositories.BankAccountRepository;
import ma.enset.digitat.banking.repositories.CustomerRepository;
import ma.enset.digitat.banking.repositories.OperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private OperationRepository operationRepository;



    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("Saving customer: {}", customer);
        //suppose we don't have a contraint bussiness for saving a customer
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer;
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(Double initialBalance, Double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null){
            throw new CustomerNotFoundException("Customer not found");
        }
        CurrentAccount currentBankAccount = new CurrentAccount();

        currentBankAccount.setId(UUID.randomUUID().toString());
        currentBankAccount.setBalance(initialBalance);
        currentBankAccount.setCreatedAt(new Date());
        currentBankAccount.setStatus(AccountStatus.CREATED);
        currentBankAccount.setOverDraft(overDraft);
        CurrentAccount savedCurrentAccount = bankAccountRepository.save(currentBankAccount);
        return savedCurrentAccount;
    }

    @Override
    public SavingAccount saveSavingBankAccount(Double initialBalance, Double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null){
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccount savingBankAccount = new SavingAccount();

        savingBankAccount.setId(UUID.randomUUID().toString());
        savingBankAccount.setBalance(initialBalance);
        savingBankAccount.setCreatedAt(new Date());
        savingBankAccount.setStatus(AccountStatus.CREATED);
        savingBankAccount.setInterestRate(interestRate);
        SavingAccount savedSavingAccount = bankAccountRepository.save(savingBankAccount);
        return savedSavingAccount;
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
