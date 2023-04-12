package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.entities.Inventory;
import com.example.InventoryManagementSystem.entities.Product;
import com.example.InventoryManagementSystem.repository.InventoryRepository;
import com.example.InventoryManagementSystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService{
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;
    @Override
    public Product addStockToProductById(Long productId, Inventory inventory) {
//        Product existingProd=productRepository.findById(productId).orElseThrow();
//        existingProd.setFkInventory(inventory);
//        productRepository.save(existingProd);

        Product existingProd=productRepository.findById(productId).orElseThrow();
        Inventory existingInventory=inventoryRepository.findById(existingProd.getFkInventory().getInventoryId()).get();

        existingInventory.setStockIn(inventory.getStockIn()+existingInventory.getStockIn());
       // existingInventory.setStockOut(inventory.getStockOut()+existingInventory.getStockOut());//NPE

        inventoryRepository.save(existingInventory);
        existingProd=productRepository.findById(productId).orElseThrow();
        return existingProd;
    }

    @Override
    public String getAllProductStockById(Long productId) {
        Product product=productRepository.findById(productId).get();
        Inventory inventory=product.getFkInventory();
        return "Product: "+product.getProductName()+" stock left: "+inventory.getStockIn();
    }

    @Override
    public Integer getStockInOfProductById(Long productId) {
        Product product=productRepository.findById(productId).get();
        Inventory inventory=product.getFkInventory();
        return inventory.getStockIn();
    }

    @Override
    public Boolean isStockAvailableCorrespondingTocustomerRequest(Integer qtyOfProducts, Long productId) {
        if(getStockInOfProductById(productId) >= qtyOfProducts){
            Inventory existObj=productRepository.findById(productId).get().getFkInventory();
                      existObj.setStockIn(existObj.getStockIn()-qtyOfProducts);//decrement to stockin
                      existObj.setStockOut(existObj.getStockOut()+qtyOfProducts);//increment to stockout means sell out
                      inventoryRepository.save(existObj);
            return true;
        }
        return false;
    }
    @Override
    public String getAllProductStock() {
        StringBuilder string=new StringBuilder();
        List<Product> allProduct=productService.getAllProduct();
        string.append("All product Stock left\n");
        for (Product product: allProduct) {
            string.append(product.getProductName()+": "+product.getFkInventory().getStockIn()+"\n");
        }
        return string.toString();
    }

}
