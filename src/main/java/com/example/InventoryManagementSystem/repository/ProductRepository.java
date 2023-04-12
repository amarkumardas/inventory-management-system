package com.example.InventoryManagementSystem.repository;

import com.example.InventoryManagementSystem.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
