package ma.enset.digitat.banking.web;

import lombok.AllArgsConstructor;
import ma.enset.digitat.banking.dtos.AccountHistoryDTO;
import ma.enset.digitat.banking.dtos.BankAccountDTO;
import ma.enset.digitat.banking.dtos.OperationDTO;
import ma.enset.digitat.banking.exceptions.BankAcountNotFoundException;
import ma.enset.digitat.banking.exceptions.CustomerNotFoundException;
import ma.enset.digitat.banking.services.BankAccountService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAcountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }
    @GetMapping("/accounts")
    public List<BankAccountDTO> listBankAccounts(){
        return bankAccountService.listBankAccounts();
    }
    @GetMapping("/accounts/{accountID}/operations")
    public List<OperationDTO> getHistory(@PathVariable String accountID) throws BankAcountNotFoundException {
        return bankAccountService.accountHistory(accountID);
    }
    @GetMapping("/accounts/{accountID}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountID
                                                , @RequestParam(name = "page",  defaultValue = "0") int page,
                                               @RequestParam(name = "size",  defaultValue = "5") int size
                                                ) throws BankAcountNotFoundException {
        return bankAccountService.getAccountHistory(accountID,page,size);
    }

}
