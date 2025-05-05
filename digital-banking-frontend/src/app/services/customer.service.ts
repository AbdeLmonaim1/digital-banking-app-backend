import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Customer} from '../models/customer.model';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  backendHost = 'http://localhost:8086';
  constructor(private http: HttpClient) { }
  public getCustomers():Observable<Array<Customer>> {
    return this.http.get<Array<Customer>>(this.backendHost+'/customers');
  }
  public searchCustomers(keyword: string):Observable<Array<Customer>> {
    return this.http.get<Array<Customer>>(this.backendHost+'/customers/search?keyword=' + keyword);
  }
  public saveCstomer(customer: Customer):Observable<Customer> {
    return this.http.post<Customer>(this.backendHost+'/customers', customer);
  }
  public deleteCustomer(id: number) {
    return this.http.delete<void>(this.backendHost+'/customers/'+id);
  }
}
