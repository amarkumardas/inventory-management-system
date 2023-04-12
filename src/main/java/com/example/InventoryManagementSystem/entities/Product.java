package com.example.InventoryManagementSystem.entities;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property ="productId")//it is added to class. to avoid infinite loop
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id",updatable = false)
    private Long productId;

    @Size(max = 50)
    @Column(name = "product_name",unique = true)
    @NotEmpty(message = "Product name cannot be empty")
    private String productName;

    @Column(name = "product_price")
    @Min(value = 1, message = "price should be greater than 0")
    @NotNull(message = "Product price cannot be null")
    private Double productPrice;
   /*By default, @ManyToMany and @OneToMany associations use the FetchType. LAZY strategy, while the @ManyToOne and @OneToOne associations
   use the FetchType. EAGER strategy. It is also possible to change the default by specifying the desired FetchType in the fetch
   attribute of the aforementioned annotations.**/
    @OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)//while giving fetchtype lazy then @JsonIdentityInfo dont work
    /*Why @jsonidentityinfo dont work when when fetch type lazy is set
      @JsonIdentityInfo is used to handle circular references in JSON serialization, but when the fetch type is set to "lazy",
      the associated entities are not loaded until they are accessed. This means that when the JSON serialization is happening,
      those entities have not yet been loaded, so they cannot be properly handled by the @JsonIdentityInfo annotation. To solve this issue,
      you can either change the fetch type to "eager" or use a DTO (Data Transfer Object) to handle the JSON serialization and break
      the circular references**/
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "fk_inventory_id")
    //@MapsId//not working it is use to specify that a field or property of an entity class should be used as primary key value of a related entity.so here productId value will be primary key of inventory class
    private Inventory fkInventory;
}
