package com.example.InventoryManagementSystem.repository;

import com.example.InventoryManagementSystem.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
}
