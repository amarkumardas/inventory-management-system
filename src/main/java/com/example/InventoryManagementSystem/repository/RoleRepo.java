package com.example.InventoryManagementSystem.repository;

import com.example.InventoryManagementSystem.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
}
