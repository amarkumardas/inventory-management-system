package com.example.InventoryManagementSystem.repository;

import com.example.InventoryManagementSystem.entities.PurchaseOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseOrdersRepository extends JpaRepository<PurchaseOrders,Long> {
//    @Query("SELECT SUM(column_name)FROM table_name WHERE condition")
//    Long getSumOfAllPurchaseProduct
}
