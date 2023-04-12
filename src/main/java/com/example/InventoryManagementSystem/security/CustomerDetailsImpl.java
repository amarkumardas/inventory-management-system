package com.example.InventoryManagementSystem.security;

import com.example.InventoryManagementSystem.entities.Customer;
import com.example.InventoryManagementSystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomerDetailsImpl implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer=this.customerRepository.findByCustomerEmail(username).orElseThrow();
        return customer;
    }
}


















/**In spring security what is userdetailssevice

 In Spring Security, UserDetailsService is an interface that is used to retrieve user details for authentication and authorization purposes.
 It is a core interface that provides the necessary information about the user, such as the username, password, and authorities (roles) for
 authentication and authorization.

 The UserDetailsService interface has only one method, loadUserByUsername(String username), which takes a username as an argument and returns
 an object that implements the UserDetails interface. The UserDetails interface provides additional information about the user, such as whether
 the account is enabled or disabled, whether the account is expired, and whether the account is locked.

 When a user tries to authenticate, the UserDetailsService is called to retrieve the user's details. Spring Security then uses the returned
 UserDetails object to authenticate the user. If the authentication is successful, the user is granted access to the requested resource based
 on their authorities (roles).*/