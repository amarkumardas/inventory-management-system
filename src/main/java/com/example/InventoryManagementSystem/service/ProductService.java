package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.entities.Product;

import java.util.List;

public interface ProductService {
Product addProduct(Product product);
Product updateProductById(Product product,Long productId);
String deleteProductById(Long productId);
List<Product> getAllProduct();
Product getProductById(Long productId);
}
