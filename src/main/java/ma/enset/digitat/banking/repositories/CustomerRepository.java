package ma.enset.digitat.banking.repositories;

import ma.enset.digitat.banking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
