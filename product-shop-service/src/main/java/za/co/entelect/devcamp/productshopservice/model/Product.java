package za.co.entelect.devcamp.productshopservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import za.co.entelect.devcamp.productshopservice.model.enums.FulfilmentType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;

import za.co.entelect.devcamp.productshopservice.model.enums.CustomerType;
import za.co.entelect.devcamp.productshopservice.model.enums.AccountType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.math.BigDecimal;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product description is required")
    private String description;

    @NotNull(message = "Fulfilment type is required")
    @Enumerated(EnumType.STRING)
    private FulfilmentType fulfilmentType;

    @NotNull(message = "Product price is required")
    private BigDecimal price;

    @NotNull(message = "Active status is required")
    private Boolean active;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<CustomerType> qualifyingCustomerTypes = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountType> qualifyingAccountTypes = new HashSet<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}