package com.example.InventoryManagementSystem.controller;

import com.example.InventoryManagementSystem.entities.Inventory;
import com.example.InventoryManagementSystem.entities.Product;
import com.example.InventoryManagementSystem.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/inventory/")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;
    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("add-stock-to-product-by-id/{productId}")
    public ResponseEntity<String> addStockToProductById(@Valid @RequestBody Inventory inventory, @PathVariable("productId")Long productId){
        Product product=inventoryService.addStockToProductById(productId,inventory);
        return new ResponseEntity<>("Stock added to Product: "+product.getProductName()+" Total stock = "+product.getFkInventory().getStockIn(), HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("get-stock-by-product-id/{productId}")
    public ResponseEntity<String> getStockByProductId(@Valid @PathVariable("productId") Long productId){
        return new ResponseEntity<>(inventoryService.getAllProductStockById(productId),HttpStatus.OK);
    }
    //@PreAuthorize("hasRole('customer')")//not working
    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("get-all-product-stock")
    public ResponseEntity<String> getAllProductStock(){
        return new ResponseEntity<>(inventoryService.getAllProductStock(),HttpStatus.OK);
    }




}
