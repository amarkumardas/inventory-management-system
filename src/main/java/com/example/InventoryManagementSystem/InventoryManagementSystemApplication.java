package com.example.InventoryManagementSystem;

import com.example.InventoryManagementSystem.entities.Customer;
import com.example.InventoryManagementSystem.entities.Role;
import com.example.InventoryManagementSystem.repository.CustomerRepository;
import com.example.InventoryManagementSystem.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class InventoryManagementSystemApplication implements CommandLineRunner {
	@Autowired
	private  PasswordEncoder passwordEncoder;

	public static void main(String[] args) {

		SpringApplication.run(InventoryManagementSystemApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(this.passwordEncoder.encode("amar"));
	}
}
