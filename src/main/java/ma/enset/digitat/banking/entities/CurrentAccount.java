package ma.enset.digitat.banking.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CurrentAccount extends BankAccount{
    private double overDraft;
}
