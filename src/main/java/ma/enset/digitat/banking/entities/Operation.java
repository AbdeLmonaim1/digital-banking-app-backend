package ma.enset.digitat.banking.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.enset.digitat.banking.enums.OperationType;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Operation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
    @ManyToOne
    private BankAccount bankAccount;
}
