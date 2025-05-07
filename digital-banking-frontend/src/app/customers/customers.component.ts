import {Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CustomerService} from '../services/customer.service';
import {Customer} from '../models/customer.model';
import {async, catchError, map, Observable, throwError} from 'rxjs';
import {FormBuilder, FormGroup} from '@angular/forms';
@Component({
  selector: 'app-customers',
  standalone: false,
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit {
  customers: Observable<Array<Customer>> | undefined;
  errorMessage!: string;
  searchFormGroup!: FormGroup;
  filteredCustomers: Customer[] = [];
  searchTerm: string = '';
  selectedCustomer: Customer | null = null;
  showDeleteModal: boolean = false;
  showEditModal: boolean = false;


  constructor(private customerService: CustomerService, private fb: FormBuilder) {

  }

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control('')
    });
    this.handleSearchCustomers()
  }


  protected readonly async = async;

  handleSearchCustomers() {
    let kw = this.searchFormGroup.value.keyword;
    this.customers = this.customerService.searchCustomers(kw).pipe(
      catchError(error => {
        this.errorMessage = error.message;
        return throwError(error);
      })
    );
  }

  handleDeleteCustomer(c: Customer) {
    let conf = confirm("Are you sure?");
    if (!conf) return;
    this.customerService.deleteCustomer(c.id).subscribe({
      next: (resp) => {
        // @ts-ignore
        this.customers = this.customers.pipe(
          map(data => {
            let index = data.indexOf(c);
            data.slice(index, 1)
            return data;
          })
        );
      },
      error: err => {
        console.log(err);
      }
    })
  }
}
