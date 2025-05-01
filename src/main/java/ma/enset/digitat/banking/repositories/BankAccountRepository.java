package ma.enset.digitat.banking.repositories;

import ma.enset.digitat.banking.entities.BankAccount;
import ma.enset.digitat.banking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

}
