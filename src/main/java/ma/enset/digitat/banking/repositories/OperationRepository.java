package ma.enset.digitat.banking.repositories;

import ma.enset.digitat.banking.entities.Customer;
import ma.enset.digitat.banking.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByBankAccountId(String accountId);
    Page<Operation> findByBankAccountIdOrderByOperationDateDesc(String accountId, Pageable pageable);

}
