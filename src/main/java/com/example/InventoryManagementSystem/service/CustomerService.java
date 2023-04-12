package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.entities.Customer;

import java.util.List;


public interface CustomerService {
    Customer addCustomer(Customer customer);
    String deleteCustomerById(Long customerId);

    Customer updateCustomerById(Customer customer,Long customerID);

    List<Customer> getAllCustomers();
    Customer getCustomerById(Long customerId);

    String changeRoleOfCustomerByCustomerIdAndRoleId(Long customerId,Long roleId);
    String addRoleOfCustomerByCustomerIdAndRoleId(Long customerId,Long roleId);
}
