package com.example.InventoryManagementSystem.controller;

import com.example.InventoryManagementSystem.entities.Product;
import com.example.InventoryManagementSystem.repository.ProductRepository;
import com.example.InventoryManagementSystem.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product/")
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("add-product")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product){
         return new ResponseEntity<>( productService.addProduct(product), HttpStatus.CREATED);
    }
    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("update-product-by-id/{productId}")
    public ResponseEntity<Product> updateProductById(@Valid @RequestBody Product product,@PathVariable("productId") Long id){
        return new ResponseEntity<>(productService.updateProductById(product,id),HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("delete-product-by-id/{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable("productId") Long productId){
        return new ResponseEntity<>(productService.deleteProductById(productId),HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('customer','admin')")
    @GetMapping("get-all-product")
    public ResponseEntity<List<Product>> getAllProduct(){
        return new ResponseEntity<>(productService.getAllProduct(),HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('customer','admin')")
    @GetMapping("get-product-by-id/{productId}")
    public  ResponseEntity<Product> getAllProduct(@PathVariable("productId") Long productId){
        return new ResponseEntity<>(productService.getProductById(productId),HttpStatus.OK);
    }

}
