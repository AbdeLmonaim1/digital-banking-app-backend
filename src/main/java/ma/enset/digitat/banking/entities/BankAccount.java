package ma.enset.digitat.banking.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import ma.enset.digitat.banking.enums.AccountStatus;

import java.util.Date;
@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder @ToString
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
}
