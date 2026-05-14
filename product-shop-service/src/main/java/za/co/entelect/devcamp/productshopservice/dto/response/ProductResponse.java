package za.co.entelect.devcamp.productshopservice.dto.response;

import lombok.Builder;
import lombok.Data;
import za.co.entelect.devcamp.productshopservice.model.enums.FulfilmentType;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private FulfilmentType fulfilmentType;
    private BigDecimal price;
    private Boolean active;
}