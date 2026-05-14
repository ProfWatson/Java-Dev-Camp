package za.co.entelect.devcamp.productshopservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import za.co.entelect.devcamp.productshopservice.model.enums.ApplicationStatus;

@Data
public class CreateProductApplicationRequest {

    @NotNull(message = "Application status is required")
    private ApplicationStatus status;

    @NotNull(message = "Customer id is required")
    private Long customerId;

    @NotNull(message = "Product id is required")
    private Long productId;
}