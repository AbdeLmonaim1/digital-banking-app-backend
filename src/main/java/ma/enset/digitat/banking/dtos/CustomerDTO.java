package ma.enset.digitat.banking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import ma.enset.digitat.banking.entities.BankAccount;

import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
}
