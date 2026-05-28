package za.co.entelect.devcamp.productshopservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import za.co.entelect.devcamp.productshopservice.dto.request.CreateProductApplicationRequest;
import za.co.entelect.devcamp.productshopservice.dto.response.ProductApplicationResponse;
import za.co.entelect.devcamp.productshopservice.dto.response.ProductEligibilityResponse;
import za.co.entelect.devcamp.productshopservice.model.ProductApplication;
import za.co.entelect.devcamp.productshopservice.service.ProductApplicationService;

import java.util.List;

@RestController
@RequestMapping("/product-applications")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProductApplicationController {

    private final ProductApplicationService productApplicationService;

    @GetMapping
    public List<ProductApplicationResponse> getApplications() {
        return productApplicationService.getApplications();
    }

    @GetMapping("/{id}")
    public ProductApplicationResponse getApplicationById(@PathVariable Long id) {
        return productApplicationService.getApplicationById(id);
    }

    @PostMapping
    public ProductApplicationResponse createApplication(@Valid @RequestBody CreateProductApplicationRequest request) {
        return productApplicationService.createApplication(request);
    }

    @GetMapping("/eligibility")
    public ProductEligibilityResponse checkEligibility(
            @RequestParam Long customerId,
            @RequestParam Long productId
    ) {
        return productApplicationService.checkEligibility(customerId, productId);
    }
}