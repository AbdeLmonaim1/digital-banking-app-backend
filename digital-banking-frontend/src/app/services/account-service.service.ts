import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AccountDetails} from '../models/account.model';

@Injectable({
  providedIn: 'root'
})
export class AccountServiceService {
  backendHost = 'http://localhost:8086';
  constructor(private http: HttpClient) { }
  public getAccount(accountID: string, page: number, size: number):Observable<AccountDetails> {
    return this.http.get<AccountDetails>(this.backendHost+'/accounts/'+accountID+'/pageOperations?page='+page+'&size='+size);
  }
  public debit(accountID: string, amount: number, description: string) {
    let data = {
      accountId: accountID,
      amount: amount,
      description: description
    }
    return this.http.post(this.backendHost+'/accounts/debit',data);
  }
  public credit(accountID: string, amount: number, description: string) {
    let data = {
      accountId: accountID,
      amount: amount,
      description: description
    }
    return this.http.post(this.backendHost+'/accounts/credit',data);
  }
  public transert(accountSource: string, accountDestination: string, amount: number, description: string) {
    let data = {
      accountSource: accountSource,
      accountDestination: accountDestination,
      amount: amount,
      description: description
    }
    return this.http.post(this.backendHost+'/accounts/transfer',data);
  }
}
