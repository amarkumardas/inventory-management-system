package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.entities.Customer;
import com.example.InventoryManagementSystem.entities.PurchaseOrders;
import com.example.InventoryManagementSystem.repository.CustomerRepository;
import com.example.InventoryManagementSystem.repository.ProductRepository;
import com.example.InventoryManagementSystem.repository.PurchaseOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class PurchaseOrdersServiceImpl implements PurchaseOrdersService {
    @Autowired
    PurchaseOrdersRepository purchaseOrdersRepository;
    @Autowired
    InventoryService inventoryService;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductService productService;
    @Override
    public String addOrders(PurchaseOrders purchaseOrders) {
        Long id=customerRepository.findById(purchaseOrders.getFkCustomer().getCustomerId()).get().getCustomerId();
        if(inventoryService.isStockAvailableCorrespondingTocustomerRequest(purchaseOrders.getProductQty(),purchaseOrders.getProductId())) {
              purchaseOrdersRepository.save(purchaseOrders);
              return "Orders added to customer: "+purchaseOrders.getFkCustomer().getCustomerId()+"\n"+"Product id: "+purchaseOrders.getProductId()+"\nProduct Qty: "+purchaseOrders.getProductQty();
        }
        return "Sorry out of stock";
    }

    @Override
    public String deleteByOrderId(Long orderId) {
//since order table ic child first deleting parent side then delete child record
        PurchaseOrders order = purchaseOrdersRepository.findById(orderId).get();
        Customer customer = order.getFkCustomer();
        customer.getFkPurchaseOrders().remove(order);
       // customerRepository.save(customer);//not needed
        purchaseOrdersRepository.delete(order);
        return "Order id: "+orderId+" deleted";
    }
    @Override
    public PurchaseOrders updateOrdersById(PurchaseOrders purchaseOrders, Long purchaseOrderId) {
            PurchaseOrders existingOrder=purchaseOrdersRepository.findById(purchaseOrderId).get();
            existingOrder.setProductId(purchaseOrders.getProductId());
            existingOrder.setProductQty(purchaseOrders.getProductQty());

           // System.out.println("---------------------------"+existingOrder.getFkCustomer().getCustomerId());
            //existingOrder.setFkCustomer(purchaseOrders.getFkCustomer());not needed becoz customerid present in existingorder obj
            purchaseOrdersRepository.save(existingOrder);
        return purchaseOrdersRepository.findById(purchaseOrderId).get();
    }

    @Override
    public PurchaseOrders getOrdersById(Long purchaseOrdersId) {
        return purchaseOrdersRepository.findById(purchaseOrdersId).get();
    }

    @Override
    public String generateBillOfACustomerById(Long customerId) {
        Customer customer=customerRepository.findById(customerId).get();
          Set<PurchaseOrders> orders=customer.getFkPurchaseOrders();
          StringBuilder str=new StringBuilder();
          str.append("Bill of CustomerID: "+customerId+"\n");
          Double subTotal=0.0;
        for (PurchaseOrders order:orders) {
            subTotal=subTotal+(order.getProductQty()*productService.getProductById(order.getProductId()).getProductPrice());
            str.append("OrderId: "+order.getOrderId()+" ProductId: "+order.getProductId()+" ProductQty: "+order.getProductQty()+
                    " Price: "+productService.getProductById(order.getProductId()).getProductPrice()+" Amount: "+(order.getProductQty()*productService.getProductById(order.getProductId()).getProductPrice())+" \n");
        }
        str.append("\n*************************************************************************\nSubTotal: "+subTotal);

        return str.toString();
    }
}
