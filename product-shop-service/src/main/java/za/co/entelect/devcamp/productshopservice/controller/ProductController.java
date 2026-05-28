package za.co.entelect.devcamp.productshopservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import za.co.entelect.devcamp.productshopservice.dto.request.CreateProductRequest;
import za.co.entelect.devcamp.productshopservice.dto.response.ProductResponse;
import za.co.entelect.devcamp.productshopservice.model.Product;
import za.co.entelect.devcamp.productshopservice.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody CreateProductRequest request) {
        return productService.createProduct(request);
    }
}