package ma.enset.digitat.banking.dtos;

import jakarta.persistence.*;
import lombok.*;
import ma.enset.digitat.banking.entities.BankAccount;
import ma.enset.digitat.banking.enums.OperationType;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OperationDTO {

    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}
