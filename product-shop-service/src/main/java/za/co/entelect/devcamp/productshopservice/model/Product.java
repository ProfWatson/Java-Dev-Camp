package za.co.entelect.devcamp.productshopservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import za.co.entelect.devcamp.productshopservice.model.enums.FulfilmentType;

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
}