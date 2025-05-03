package ma.enset.digitat.banking.mappers;

import ma.enset.digitat.banking.dtos.CustomerDTO;
import ma.enset.digitat.banking.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
//MapStruct is a code generator that greatly simplifies the implementation of mappings between Java bean types based on a convention over configuration approach.
@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer){
        if (customer == null) return null;
        CustomerDTO customerDTO = new CustomerDTO();
//        customerDTO.setId(customer.getId());
//        customerDTO.setName(customer.getName());
//        customerDTO.setEmail(customer.getEmail());
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        if (customerDTO == null) return null;
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }
}
