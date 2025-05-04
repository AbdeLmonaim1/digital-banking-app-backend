package ma.enset.digitat.banking.mappers;

import ma.enset.digitat.banking.dtos.*;
import ma.enset.digitat.banking.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
//MapStruct is a code generator that greatly simplifies the implementation of mappings between Java bean types based on a convention over configuration approach.
@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer){
        if (customer == null) return null;
        CustomerDTO customerDTO = new CustomerDTO();
//        customerDTO.setId(customer.getId());
//        customerDTO.setName(customer.getName());
//        customerDTO.setEmail(customer.getEmail());
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        if (customerDTO == null) return null;
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }
    public SavingBankAccountDTO fromSavingAccount(SavingAccount savingBankAccount){
        if (savingBankAccount == null) return null;
        SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingBankAccount,savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(fromCustomer(savingBankAccount.getCustomer()));
        savingBankAccountDTO.setType(savingBankAccount.getClass().getSimpleName());
        return savingBankAccountDTO;
    }
    public SavingAccount fromSavingAccountDTO(SavingBankAccountDTO savingBankAccountDTO){
        if (savingBankAccountDTO == null) return null;
        SavingAccount savingBankAccount = new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO,savingBankAccount);
        savingBankAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));
        return savingBankAccount;
    }
    public CurrentBankAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
        if (currentAccount == null) return null;
        CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDTO;
    }
    public CurrentAccount fromCurrentAccountDTO(CurrentBankAccountDTO currentBankAccountDTO){
        if (currentBankAccountDTO == null) return null;
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;
    }
    public BankAccountDTO fromBankAccount(CurrentAccount currentAccount){
        if (currentAccount == null) return null;
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        BeanUtils.copyProperties(currentAccount,bankAccountDTO);
        bankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        return bankAccountDTO;
    }
    public BankAccount fromBankAccountDTO(BankAccountDTO bankAccountDTO){
        if (bankAccountDTO == null) return null;
        BankAccount bankAccount = new BankAccount();
        BeanUtils.copyProperties(bankAccountDTO,bankAccount);
        bankAccount.setCustomer(fromCustomerDTO(bankAccountDTO.getCustomerDTO()));
        return bankAccount;
    }
    public OperationDTO fromOperation(Operation operation){
        if (operation == null) return null;
        OperationDTO operationDTO = new OperationDTO();
        BeanUtils.copyProperties(operation,operationDTO);
        return operationDTO;
    }
    public Operation fromOperationDTO(OperationDTO operationDTO){
        if (operationDTO == null) return null;
        Operation operation = new Operation();
        BeanUtils.copyProperties(operationDTO,operation);
        return operation;
    }
}
