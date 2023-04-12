package com.example.InventoryManagementSystem.service;
import com.example.InventoryManagementSystem.entities.Product;
import com.example.InventoryManagementSystem.repository.InventoryRepository;
import com.example.InventoryManagementSystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepository productRepository;
    @Autowired
    InventoryRepository inventoryRepository;
    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
    @Override
    public Product updateProductById(Product product,Long productId) {
       Product existingProduct=productRepository.findById(productId).orElseThrow();
       existingProduct.setProductName(product.getProductName());
       existingProduct.setProductPrice(product.getProductPrice());
       existingProduct.setFkInventory(product.getFkInventory());
       return productRepository.save(existingProduct);
    }

    @Override
    public String deleteProductById(Long productId) {
        productRepository.delete(productRepository.findById(productId).get());
        return "Product Id- "+productId+" Deleted";
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).get();
    }
}
