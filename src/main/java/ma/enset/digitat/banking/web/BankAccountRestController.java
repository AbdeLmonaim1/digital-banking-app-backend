package ma.enset.digitat.banking.web;

import lombok.AllArgsConstructor;
import ma.enset.digitat.banking.dtos.*;
import ma.enset.digitat.banking.exceptions.BalanceNotSufficientException;
import ma.enset.digitat.banking.exceptions.BankAccountNotFoundException;
import ma.enset.digitat.banking.exceptions.CustomerNotFoundException;
import ma.enset.digitat.banking.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/accounts/{accountId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }
    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<BankAccountDTO> listBankAccounts(){
        return bankAccountService.listBankAccounts();
    }
    @GetMapping("/accounts/{accountID}/operations")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<OperationDTO> getHistory(@PathVariable String accountID) throws BankAccountNotFoundException {
        return bankAccountService.accountHistory(accountID);
    }
    @GetMapping("/accounts/{accountID}/pageOperations")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountID
                                                , @RequestParam(name = "page",  defaultValue = "0") int page,
                                               @RequestParam(name = "size",  defaultValue = "5") int size
                                                ) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountID,page,size);
    }
    @PostMapping("/accounts/debit")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException, CustomerNotFoundException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }
    @PostMapping("/accounts/credit")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException, CustomerNotFoundException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException, CustomerNotFoundException {
        this.bankAccountService.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount());
    }

}
