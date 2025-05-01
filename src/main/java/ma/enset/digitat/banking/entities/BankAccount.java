package ma.enset.digitat.banking.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.enset.digitat.banking.enums.AccountStatus;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING, length = 4)
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder @ToString
public class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
//    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.EAGER)
    private List<Operation> operations;
}
