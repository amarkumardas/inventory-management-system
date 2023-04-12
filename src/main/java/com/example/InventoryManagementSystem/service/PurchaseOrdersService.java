package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.entities.PurchaseOrders;

public interface PurchaseOrdersService {
    String addOrders(PurchaseOrders purchaseOrders);
    String deleteByOrderId(Long orderId);
    PurchaseOrders updateOrdersById(PurchaseOrders purchaseOrders,Long purchaseOrderId);

    PurchaseOrders getOrdersById(Long purchaseOrdersId);

    String generateBillOfACustomerById(Long customerId);

}
