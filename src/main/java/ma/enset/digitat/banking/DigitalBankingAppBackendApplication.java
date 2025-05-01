package ma.enset.digitat.banking;

import ma.enset.digitat.banking.entities.*;
import ma.enset.digitat.banking.enums.AccountStatus;
import ma.enset.digitat.banking.enums.OperationType;
import ma.enset.digitat.banking.repositories.BankAccountRepository;
import ma.enset.digitat.banking.repositories.CustomerRepository;
import ma.enset.digitat.banking.repositories.OperationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication
public class DigitalBankingAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingAppBackendApplication.class, args);
    }
//    @Bean
    CommandLineRunner commandLineRunner(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository, OperationRepository operationRepository) {
        return args -> {
            Customer customer1 = Customer.builder()
                    .name("Monaim")
                    .email("monaim.ahd@example.com")
                    .build();
            Customer customer2 = Customer.builder()
                    .name("Amine")
                    .email("amine.gha@example.com")
                    .build();
            customerRepository.save(customer1);
            customerRepository.save(customer2);
            customerRepository.findAll().forEach(customer -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random() * 9000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setOverDraft(9000);
                currentAccount.setCustomer(customer);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random() * 9000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setInterestRate(5.5);
                savingAccount.setCustomer(customer);
                bankAccountRepository.save(savingAccount);
            });
        };
    }
//    @Bean
    CommandLineRunner runner(BankAccountRepository bankAccountRepository, OperationRepository operationRepository){
        return args -> {
            bankAccountRepository.findAll().forEach(acc ->{
                for (int i = 0; i < 5; i++) {
                    Operation operation = Operation.builder()
                            .type(Math.random()>0.5 ? OperationType.DEBIT : OperationType.CREDIT)
                            .amount(Math.random() * 10000)
                            .operationDate(new Date())
                            .description( "Operation " + i)
                            .bankAccount(acc)
                            .build();
                    operationRepository.save(operation);
                }
            });
        };
    }
//    @Bean
    CommandLineRunner commandLineRunner(BankAccountRepository bankAccountRepository){
        return args -> {
            BankAccount bankAccount = bankAccountRepository.findById("09426b0d-05ef-4e0c-8db3-a6572d3207d6").orElse(null);
            System.out.println("******************About Bank Account*********************");
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getCreatedAt());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println("******************Type of Bank Account*********************");
            if(bankAccount instanceof CurrentAccount){
                System.out.println("Current Account");
                System.out.println(((CurrentAccount) bankAccount).getOverDraft());
            }
            if(bankAccount instanceof SavingAccount){
                System.out.println("Saving Account");
                System.out.println(((SavingAccount) bankAccount).getInterestRate());
            }
            System.out.println("******************Operations*********************");
            bankAccount.getOperations().forEach(op -> {
                System.out.println("Operation Type: " + op.getType());
                System.out.println("Operation Amount: " + op.getAmount());
                System.out.println("Operation Date: " + op.getOperationDate());
                System.out.println("Operation Description: " + op.getDescription());
            });
        };
    }
}
