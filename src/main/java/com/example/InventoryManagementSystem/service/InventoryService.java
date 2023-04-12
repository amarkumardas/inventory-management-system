package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.entities.Inventory;
import com.example.InventoryManagementSystem.entities.Product;

public interface InventoryService {
Product addStockToProductById(Long productId, Inventory inventory);
 String getAllProductStock();
 String getAllProductStockById(Long productId);

 Integer getStockInOfProductById(Long productId);

 Boolean isStockAvailableCorrespondingTocustomerRequest(Integer qtyOfProducts,Long productId);

}
