package com.example.InventoryManagementSystem.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "inventory_id",updatable = false)
    private Long inventoryId;

    @Column(name="stock_in")
    private Integer stockIn;


    @Column(name="stock_out")
    private Integer stockOut;

    @OneToOne(mappedBy = "fkInventory",fetch= FetchType.EAGER)
    private Product fkProductId;

}
