package ma.enset.digitat.banking.dtos;


import lombok.*;

import ma.enset.digitat.banking.enums.AccountStatus;

import java.util.Date;
import java.util.List;
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString
public class SavingBankAccountDTO extends BankAccountDTO{
    private double interestRate;
}
