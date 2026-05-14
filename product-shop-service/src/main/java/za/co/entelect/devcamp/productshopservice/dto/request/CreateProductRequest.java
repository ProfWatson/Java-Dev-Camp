package za.co.entelect.devcamp.productshopservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import za.co.entelect.devcamp.productshopservice.model.enums.FulfilmentType;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product description is required")
    private String description;

    @NotNull(message = "Fulfilment type is required")
    private FulfilmentType fulfilmentType;

    @NotNull(message = "Product price is required")
    private BigDecimal price;

    @NotNull(message = "Active status is required")
    private Boolean active;
}