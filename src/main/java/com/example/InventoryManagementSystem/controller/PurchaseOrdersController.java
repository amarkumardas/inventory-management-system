package com.example.InventoryManagementSystem.controller;

import com.example.InventoryManagementSystem.entities.PurchaseOrders;
import com.example.InventoryManagementSystem.repository.CustomerRepository;
import com.example.InventoryManagementSystem.service.PurchaseOrdersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/purchaseorders/")
public class PurchaseOrdersController {
    @Autowired
    PurchaseOrdersService purchaseOrdersService;
    @Autowired
    CustomerRepository customerRepository;
    @PreAuthorize("hasAnyAuthority('admin','customer')")
    @PostMapping("add-orders")
    public ResponseEntity<String> addOrders(@Valid @RequestBody PurchaseOrders purchaseOrders){
        return new ResponseEntity<>(purchaseOrdersService.addOrders(purchaseOrders), HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyAuthority('admin','customer')")
    @PutMapping("update-orders-by-id/{purchaseOrderID}")
    public ResponseEntity<PurchaseOrders> updateOrdersById(@Valid @RequestBody PurchaseOrders purchaseOrders,@PathVariable("purchaseOrderID")Long purchaseOrderID){
        return new ResponseEntity<>(purchaseOrdersService.updateOrdersById(purchaseOrders,purchaseOrderID), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('admin','customer')")
    @DeleteMapping("delete-by-order-id/{orderId}")
    public ResponseEntity<String> deleteByOrderId(@PathVariable("orderId") Long orderId){
        return new ResponseEntity<>(purchaseOrdersService.deleteByOrderId(orderId),HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('admin','customer')")
    @GetMapping("get-orders-by-id/{orderId}")
    public ResponseEntity<PurchaseOrders> getOrdersById(@PathVariable("orderId")Long orderId){
        return new ResponseEntity<>(purchaseOrdersService.getOrdersById(orderId),HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('admin','customer')")
    @GetMapping("get-generated-bill")
    public ResponseEntity<String> generateBillOfACustomerById(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();////to get current logged username from springboot security environment
        Long userID=customerRepository.findByCustomerEmail(authentication.getName()).get().getCustomerId();


        return new ResponseEntity<>(purchaseOrdersService.generateBillOfACustomerById(userID),HttpStatus.OK);
    }

}
