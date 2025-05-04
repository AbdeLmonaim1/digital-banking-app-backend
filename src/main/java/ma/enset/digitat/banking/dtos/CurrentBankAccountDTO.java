package ma.enset.digitat.banking.dtos;


import lombok.*;
import ma.enset.digitat.banking.enums.AccountStatus;

import java.util.Date;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter  @ToString
public class CurrentBankAccountDTO extends BankAccountDTO {
    private double overDraft;
}
