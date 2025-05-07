import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators,FormControl} from '@angular/forms';
import {CustomerService} from '../services/customer.service';
import {Customer} from '../models/customer.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-new-customer',
  standalone: false,
  templateUrl: './new-customer.component.html',
  styleUrl: './new-customer.component.css'
})
export class NewCustomerComponent implements OnInit {
  public newCustomerFormGroup!: FormGroup;
  constructor(private fb: FormBuilder, private customerService: CustomerService, private router: Router) {

  }
  ngOnInit(): void {
    this.newCustomerFormGroup = this.fb.group({
      name: this.fb.control('', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]),
      email: this.fb.control('', [Validators.required, Validators.email]),
    })
  }

  handleSaveCustomer() {
    let customer: Customer = this.newCustomerFormGroup?.value;
    this.customerService.saveCstomer(customer).subscribe({
      next: (data) => {
        alert("Customer saved successfully");
        this.newCustomerFormGroup?.reset();
        this.router.navigateByUrl("/customers");
      },
      error: (err) => {
        console.log(err);
      }
    })
  }
}
