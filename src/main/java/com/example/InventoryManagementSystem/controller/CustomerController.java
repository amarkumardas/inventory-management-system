package com.example.InventoryManagementSystem.controller;

import com.example.InventoryManagementSystem.advice.CannotAccessIdException;
import com.example.InventoryManagementSystem.entities.Customer;
import com.example.InventoryManagementSystem.repository.CustomerRepository;
import com.example.InventoryManagementSystem.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer/")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerRepository customerRepository;
    @PreAuthorize("hasAnyAuthority('admin','customer')")
    @PostMapping
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer){
        return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyAuthority('admin','customer')")
    @GetMapping("get-customer-by-id/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId")Long customerId) throws CannotAccessIdException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();////to get current logged username from springboot security environment
        Long userID=customerRepository.findByCustomerEmail(authentication.getName()).get().getCustomerId();

        if(userID != customerId && authentication.getAuthorities().contains(new SimpleGrantedAuthority("customer")))//only customer which is login then he can see its own details but not other details
            throw new CannotAccessIdException("cannot see other customer details");

        return new ResponseEntity<>(customerService.getCustomerById(customerId),HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("get-all-customers")
    public ResponseEntity<List<Customer>> getAllCustomers( ){
        return new ResponseEntity<>(customerService.getAllCustomers(),HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('admin','customer')")
    @PutMapping("update-customer-by-id/{customerId}")
    public ResponseEntity<Customer> updateCustomerById(@Valid @RequestBody Customer customer,@PathVariable("customerId")Long customerId) throws CannotAccessIdException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();////to get current logged username from springboot security environment
        Long userID=customerRepository.findByCustomerEmail(authentication.getName()).get().getCustomerId();

        if(userID != customerId && authentication.getAuthorities().contains(new SimpleGrantedAuthority("customer")))//only customer which is login then he can see its own details but not other details
            throw new CannotAccessIdException("cannot update other customer details");

        return new ResponseEntity<>(customerService.updateCustomerById(customer,customerId),HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("delete-customer-by-id/{customerId}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable("customerId") Long customerId)   {
        return new ResponseEntity<>(customerService.deleteCustomerById(customerId),HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("change-role-of-customer-by-customer-id-and-role-id/{customerId}/{roleId}")
    public ResponseEntity<String> changeRoleOfCustomerByCustomerIdAndRoleId(@PathVariable("customerId") Long customerId,@PathVariable("roleId") Long roleId){
        return new ResponseEntity<>(customerService.changeRoleOfCustomerByCustomerIdAndRoleId(customerId,roleId),HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("add-role-of-customer-by-customer-id-and-role-id/{customerId}/{roleId}")
    public ResponseEntity<String> addRoleOfCustomerByCustomerIdAndRoleId(@PathVariable("customerId") Long customerId,@PathVariable("roleId") Long roleId){
        return new ResponseEntity<>(customerService.addRoleOfCustomerByCustomerIdAndRoleId(customerId,roleId),HttpStatus.OK);
    }


}
