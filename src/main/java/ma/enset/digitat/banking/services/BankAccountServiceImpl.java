package ma.enset.digitat.banking.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.digitat.banking.dtos.CustomerDTO;
import ma.enset.digitat.banking.entities.*;
import ma.enset.digitat.banking.enums.AccountStatus;
import ma.enset.digitat.banking.enums.OperationType;
import ma.enset.digitat.banking.exceptions.BalanceNotSufficientException;
import ma.enset.digitat.banking.exceptions.BankAcountNotFoundException;
import ma.enset.digitat.banking.exceptions.CustomerNotFoundException;
import ma.enset.digitat.banking.mappers.BankAccountMapperImpl;
import ma.enset.digitat.banking.repositories.BankAccountRepository;
import ma.enset.digitat.banking.repositories.CustomerRepository;
import ma.enset.digitat.banking.repositories.OperationRepository;
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
    private BankAccountMapperImpl dtoMapper;



    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {

        log.info("Saving customer: {}", customerDTO);
        //suppose we don't have a contraint bussiness for saving a customer
        Customer savedCustomer = customerRepository.save(dtoMapper.fromCustomerDTO(customerDTO));
        return dtoMapper.fromCustomer(savedCustomer);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer savedCustomer = customerRepository.save(dtoMapper.fromCustomerDTO(customerDTO));
        return dtoMapper.fromCustomer(savedCustomer);
    }
    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        CustomerDTO customer = this.getCustomer(customerId);
        customerRepository.deleteById(customerId);
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
        if(customer == null) return null;
        CustomerDTO customerDTO = dtoMapper.fromCustomer(customer);
        return customerDTO;
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
        currentBankAccount.setCustomer(customer);
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
        savingBankAccount.setCustomer(customer);
        SavingAccount savedSavingAccount = bankAccountRepository.save(savingBankAccount);
        return savedSavingAccount;
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = customers.stream()
                .map(customer -> dtoMapper.fromCustomer(customer))
                .toList();
        return customerDTOS;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws CustomerNotFoundException, BankAcountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAcountNotFoundException("Bank account not found"));
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws CustomerNotFoundException, BankAcountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = getBankAccount(accountId);
        if(bankAccount.getBalance() < amount){
            throw new BalanceNotSufficientException("Balance not sufficient");
        }
        Operation operation = new Operation();
        operation.setType(OperationType.DEBIT);
        operation.setAmount(amount);
        operation.setOperationDate(new Date());
        operation.setDescription(description);
        operation.setBankAccount(bankAccount);
        operationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws CustomerNotFoundException, BankAcountNotFoundException {
        BankAccount bankAccount = getBankAccount(accountId);
        Operation operation = new Operation();
        operation.setType(OperationType.CREDIT);
        operation.setAmount(amount);
        operation.setOperationDate(new Date());
        operation.setDescription(description);
        operation.setBankAccount(bankAccount);
        operationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws CustomerNotFoundException, BalanceNotSufficientException, BankAcountNotFoundException {
        debit(accountIdSource, amount, "Transfer to " + accountIdDestination);
        credit(accountIdDestination, amount, "Transfer from " + accountIdSource);
    }
    @Override
    public List<BankAccount> listBankAccounts() {
        return bankAccountRepository.findAll();
    }

}
