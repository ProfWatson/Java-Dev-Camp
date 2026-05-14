package za.co.entelect.devcamp.productshopservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.entelect.devcamp.productshopservice.dto.request.CreateProductRequest;
import za.co.entelect.devcamp.productshopservice.dto.response.ProductResponse;
import za.co.entelect.devcamp.productshopservice.exception.ResourceNotFoundException;
import za.co.entelect.devcamp.productshopservice.model.Product;
import za.co.entelect.devcamp.productshopservice.repository.ProductRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getProducts() {
        log.info("Fetching all products");

        List<ProductResponse> products = productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

        log.info("Fetched {} products", products.size());

        return products;
    }

    public ProductResponse getProductById(Long id) {
        log.info("Fetching product with id: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found with id: {}", id);
                    return new ResourceNotFoundException("Product not found with id: " + id);
                });

        return mapToResponse(product);
    }

    public ProductResponse createProduct(CreateProductRequest request) {

        log.info("Creating product with name: {}", request.getName());

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setFulfilmentType(request.getFulfilmentType());
        product.setPrice(request.getPrice());
        product.setActive(request.getActive());

        Product savedProduct = productRepository.save(product);

        log.info("Created product with id: {}", savedProduct.getId());

        return mapToResponse(savedProduct);
    }

    public ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .fulfilmentType(product.getFulfilmentType())
                .price(product.getPrice())
                .active(product.getActive())
                .build();
    }
}