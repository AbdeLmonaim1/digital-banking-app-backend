import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AccountServiceService} from '../services/account-service.service';
import {async, catchError, Observable, throwError} from 'rxjs';
import {AccountDetails} from '../models/account.model';
import {AuthService} from '../services/auth.service';

@Component({
  selector: 'app-accounts',
  standalone: false,
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent implements OnInit{
  accountsFormGroup!: FormGroup;
  operationFormGroup!: FormGroup;
  currentPage: number=0;
  pageSize: number=5;
  accountObservable!: Observable<AccountDetails>;
  errorMessage!: string;
  constructor(private fb: FormBuilder, private accountService: AccountServiceService, public authService: AuthService) {
  }

  ngOnInit(): void {
    this.accountsFormGroup = this.fb.group({
      accountID: this.fb.control('')
    })
    this.operationFormGroup = this.fb.group({
      operationType: this.fb.control(null),
      amount: this.fb.control(0),
      description: this.fb.control(null),
      accountDestination: this.fb.control(null)
    })
  }

  handleSearchAccounts() {
    let accountID = this.accountsFormGroup.value.accountID;
    this.accountObservable = this.accountService.getAccount(accountID, this.currentPage, this.pageSize).pipe(
      catchError(err =>{
        this.errorMessage = err.message;
        return throwError(err);
      })
    );
  }

  protected readonly async = async;

  goToPage(page: number) {
    this.currentPage = page;
    this.handleSearchAccounts()
  }

  handleAccountOperation() {
    let accountID = this.accountsFormGroup.value.accountID;
    let operationType = this.operationFormGroup.value.operationType;
    if(operationType==='DEBIT') {
      this.accountService.debit(accountID, this.operationFormGroup.value.amount, this.operationFormGroup.value.description).subscribe({
        next: () =>{
          alert("DEBIT operation successful");
          this.handleSearchAccounts();
        },
        error: (err) => {
          alert("DEBIT operation failed");

        }
      });
    }else if(operationType==='CREDIT') {
      this.accountService.credit(accountID, this.operationFormGroup.value.amount, this.operationFormGroup.value.description).subscribe({
        next: () =>{
          alert("CREDIT operation successful");
          this.handleSearchAccounts();
        },
        error: (err) => {
          alert("CREDIT operation failed");
        }
      });
    }else if(operationType==='TRANSFER') {
      this.accountService.transert(accountID, this.operationFormGroup.value.accountDestination, this.operationFormGroup.value.amount, this.operationFormGroup.value.description).subscribe({
        next: () =>{
          alert("TRANSFER operation successful");
          this.handleSearchAccounts();
        },
        error: (err) => {
          alert("TRANSFER operation failed");
        }
      });
    }
    this.operationFormGroup.reset();
  }

  protected readonly focus = focus;
}
