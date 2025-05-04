package ma.enset.digitat.banking.dtos;


import lombok.*;
import ma.enset.digitat.banking.enums.AccountStatus;

import java.util.Date;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder @ToString
public class BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private String type;

}
