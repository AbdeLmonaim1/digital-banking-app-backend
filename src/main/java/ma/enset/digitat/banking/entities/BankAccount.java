package ma.enset.digitat.banking.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.enset.digitat.banking.enums.AccountStatus;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder @ToString
public class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount")
    private List<Operation> operations;
}
