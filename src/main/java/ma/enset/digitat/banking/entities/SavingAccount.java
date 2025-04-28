package ma.enset.digitat.banking.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder @ToString
public class SavingAccount extends BankAccount {
    private double interestRate;
}
