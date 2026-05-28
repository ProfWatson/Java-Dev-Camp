package za.co.entelect.devcamp.productshopservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ProductEligibilityResponse {

    private boolean eligible;

    private String reason;

    @Builder.Default
    private List<String> failedChecks = new ArrayList<>();
}