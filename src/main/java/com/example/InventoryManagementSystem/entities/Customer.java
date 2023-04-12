package com.example.InventoryManagementSystem.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property ="customerId")
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="customer_id",updatable = false)
    private Long customerId;

    @Column(name = "email",nullable = false,unique = true)//every username should be unique
    @Email(message = "Invalid email")
    @NotEmpty(message = "Email cannot be empty")//@NotNull not working
    private String customerEmail;

    @Column(name = "customer_name")
    @NotEmpty(message = "name cannot be empty")
    private String customerName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)// indicate that a property should be writable by the client, but not readable. This means that the property can be included in a JSON request to update the corresponding record in the database, but it will not be returned in the response from the server
    @Column(name = "customer_password",nullable = false)
    @NotBlank(message = "Password is mandatory")
    @Size(min=3,max = 256,message = "Password must be minimum of 3 characters and maximum of 256 characters")
    private String customerPassword;

    @JsonIgnore
    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)//data will be loaded on the spot
    private Set<Role> roles=new HashSet<>();

    @Column(name = "contact_number")
    @Size(min=10,message = "incorrect contact number")
    private String contactNumber;
   //parent class
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//The access property with a value of "writeonly" in a JSON schema definition is used to indicate that a property should be writable by the client, but not readable. This means that the property can be included in a JSON request to update the corresponding record in the database, but it will not be returned in the response from the server.In Spring Boot, the @JsonProperty annotation is used to specify how a field in a Java object should be serialized or deserialized when it is converted   to or from JSON. The "access" attribute of the annotation can be used to specify whether the field should be included in the JSON when the object is serialized, or whether it should be set when the object is deserialized.If the "access" attribute is set to JsonProperty.Access.WRITE_ONLY, it means that the field will be included in the JSON when the object is serialized, but will not be set when the object is deserialized. This can be useful in cases where the value of the field should not be provided by external clients, but should be generated by the application. For example, if a field contains a password, it may be set as write-only to prevent the password from being deserialized and potentially exposed.
    @OneToMany(mappedBy = "fkCustomer",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)//by default fetch type is lazy for onetomany
    private Set<PurchaseOrders> fkPurchaseOrders;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities=this.roles.stream().map((role -> new SimpleGrantedAuthority(role.getRoleName()))).collect(Collectors.toList());
        return authorities;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getPassword() {
        return this.customerPassword;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.customerEmail;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
