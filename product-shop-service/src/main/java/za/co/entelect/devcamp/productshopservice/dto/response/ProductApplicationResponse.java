package za.co.entelect.devcamp.productshopservice.dto.response;

import lombok.Builder;
import lombok.Data;
import za.co.entelect.devcamp.productshopservice.model.enums.ApplicationStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductApplicationResponse {

    private Long id;
    private ApplicationStatus status;
    private LocalDateTime createdDate;
    private CustomerResponse customer;
    private ProductResponse product;
}