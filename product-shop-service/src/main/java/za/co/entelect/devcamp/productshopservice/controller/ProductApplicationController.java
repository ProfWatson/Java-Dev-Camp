package za.co.entelect.devcamp.productshopservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import za.co.entelect.devcamp.productshopservice.dto.request.CreateProductApplicationRequest;
import za.co.entelect.devcamp.productshopservice.dto.response.ProductApplicationResponse;
import za.co.entelect.devcamp.productshopservice.model.ProductApplication;
import za.co.entelect.devcamp.productshopservice.service.ProductApplicationService;

import java.util.List;

@RestController
@RequestMapping("/product-applications")
@RequiredArgsConstructor
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
}