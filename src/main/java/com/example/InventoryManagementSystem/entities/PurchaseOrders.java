package com.example.InventoryManagementSystem.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "purchase_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrders {//child class
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id",updatable = false)
    private Long orderId;

    @Column(name="product_id",nullable = false)
    @NotNull(message = "product id cannot be null")
    private Long productId;

    @Column(name = "product_qty",nullable = false)
    @Range(min = 1,message = "Number of quantity must be greater than 1")
    private Integer productQty;
    //@ManyToOne(cascade = CascadeType.ALL)//gives error ni
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne //by default fetch type is eager
    @JoinColumn(name = "fk_customer")
    private Customer fkCustomer;

}
