package ma.enset.digitat.banking.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.digitat.banking.dtos.*;
import ma.enset.digitat.banking.entities.*;
import ma.enset.digitat.banking.enums.AccountStatus;
import ma.enset.digitat.banking.enums.OperationType;
import ma.enset.digitat.banking.exceptions.BalanceNotSufficientException;
import ma.enset.digitat.banking.exceptions.BankAccountNotFoundException;
import ma.enset.digitat.banking.exceptions.CustomerNotFoundException;
import ma.enset.digitat.banking.mappers.BankAccountMapperImpl;
import ma.enset.digitat.banking.repositories.BankAccountRepository;
import ma.enset.digitat.banking.repositories.CustomerRepository;
import ma.enset.digitat.banking.repositories.OperationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public CurrentBankAccountDTO saveCurrentBankAccount(Double initialBalance, Double overDraft, Long customerId) throws CustomerNotFoundException {
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
        return dtoMapper.fromCurrentAccount(savedCurrentAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(Double initialBalance, Double interestRate, Long customerId) throws CustomerNotFoundException {
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
        return dtoMapper.fromSavingAccount(savedSavingAccount);
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
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
        if(bankAccount instanceof CurrentAccount){
            return dtoMapper.fromCurrentAccount((CurrentAccount) bankAccount);
        }
        return dtoMapper.fromSavingAccount((SavingAccount) bankAccount);
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
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
    public void credit(String accountId, double amount, String description) throws  BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
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
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws CustomerNotFoundException, BalanceNotSufficientException, BankAccountNotFoundException {
        debit(accountIdSource, amount, "Transfer to " + accountIdDestination);
        credit(accountIdDestination, amount, "Transfer from " + accountIdSource);
    }
    @Override
    public List<BankAccountDTO> listBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof CurrentAccount) {
                return dtoMapper.fromCurrentAccount((CurrentAccount) bankAccount);
            } else {
                return dtoMapper.fromSavingAccount((SavingAccount) bankAccount);
            }
        }).toList();
        return bankAccountDTOS;
    }
    @Override
    public List<OperationDTO> accountHistory(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
        List<Operation> operationList = operationRepository.findByBankAccountId(accountId);
        List<OperationDTO> operationDTOS = operationList.stream().map(operation -> dtoMapper.fromOperation(operation)).toList();
        return operationDTOS;
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountID, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountID).orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
        Page<Operation> accountOperations = operationRepository.findByBankAccountIdOrderByOperationDateDesc(accountID, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        List<OperationDTO> operationDTOS = accountOperations.stream().map(op -> dtoMapper.fromOperation(op)).toList();
        accountHistoryDTO.setOperations(operationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        return customerRepository.findByNameContains(keyword).stream().map(customer -> dtoMapper.fromCustomer(customer)).toList();
    }


}
