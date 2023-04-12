package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.entities.Customer;
import com.example.InventoryManagementSystem.entities.PurchaseOrders;
import com.example.InventoryManagementSystem.entities.Role;
import com.example.InventoryManagementSystem.repository.CustomerRepository;
import com.example.InventoryManagementSystem.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

   public CustomerServiceImpl(CustomerRepository customerRepository){
       this.customerRepository=customerRepository;
   }
    @Override
    public Customer addCustomer(Customer newCustomer) {
        newCustomer.setCustomerPassword(this.passwordEncoder.encode(newCustomer.getCustomerPassword()));

        List<Customer> customer=customerRepository.findAll();
        if (!customer.isEmpty()){//if customer available then make role as normal customer
            Role roles=roleRepo.findById(2L).orElseThrow();
            Set<Role> roleObj=new HashSet<>();
            roleObj.add(roles);
            newCustomer.setRoles(roleObj);
            return customerRepository.save(newCustomer);
        }
        else {//if customer not available then make role as admin

            Role roles=roleRepo.findById(1l).orElseThrow();//by default first customer role is admin
            Set<Role> roleObj=new HashSet<>();
            roleObj.add(roles);
            newCustomer.setRoles(roleObj);
            return customerRepository.save(newCustomer);
        }
    }
    @Override
    public String deleteCustomerById(Long customerId) {
       Customer existingCustomer=customerRepository.findById(customerId).get();
       existingCustomer.getRoles().clear();//first clear role before delete due to foreign key constraint
          customerRepository.delete(customerRepository.findById(customerId).get());
          return "Deleted customer id: "+customerId;
    }
    @Override
    public Customer updateCustomerById(Customer customer, Long customerID) {
        Customer existingCustomer=customerRepository.findById(customerID).get();
        existingCustomer.setCustomerEmail(customer.getCustomerEmail());
        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setContactNumber(customer.getContactNumber());
        existingCustomer.setCustomerPassword(this.passwordEncoder.encode(customer.getCustomerPassword()));
        customerRepository.save(existingCustomer);
        return customerRepository.findById(customerID).get();
    }
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).get();
    }

    @Override
    public String changeRoleOfCustomerByCustomerIdAndRoleId(Long customerId,Long roleId) {
       Customer existingCustomer=customerRepository.findById(customerId).get();
        Role roles=roleRepo.findById(roleId).orElseThrow();//by default first customer role is admin
        Set<Role> roleObj=new HashSet<>();
        roleObj.add(roles);
        existingCustomer.setRoles(roleObj);
        customerRepository.save(existingCustomer);
          return "Role: "+roles.getRoleName()+" updated to CustomerId: "+existingCustomer.getCustomerId();
    }
    @Override
    public String addRoleOfCustomerByCustomerIdAndRoleId(Long customerId,Long roleId) {
        Customer existingCustomer=customerRepository.findById(customerId).get();
        Role roles=roleRepo.findById(roleId).orElseThrow();//by default first customer role is admin
        Set<Role> roleObj=new HashSet<>();
        roleObj.add(roles);
        existingCustomer.getRoles().add(roles);
        customerRepository.save(existingCustomer);
        return "Role: "+roles.getRoleName()+" added to CustomerId: "+existingCustomer.getCustomerId();
    }
}
