package za.co.entelect.devcamp.productshopservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.entelect.devcamp.productshopservice.dto.request.CreateProductApplicationRequest;
import za.co.entelect.devcamp.productshopservice.dto.response.ProductApplicationResponse;
import za.co.entelect.devcamp.productshopservice.dto.response.ProductEligibilityResponse;
import za.co.entelect.devcamp.productshopservice.exception.ResourceNotFoundException;
import za.co.entelect.devcamp.productshopservice.model.Customer;
import za.co.entelect.devcamp.productshopservice.model.Product;
import za.co.entelect.devcamp.productshopservice.model.ProductApplication;
import za.co.entelect.devcamp.productshopservice.repository.CustomerRepository;
import za.co.entelect.devcamp.productshopservice.repository.ProductApplicationRepository;
import za.co.entelect.devcamp.productshopservice.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductApplicationService {

    private final ProductApplicationRepository productApplicationRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    public List<ProductApplicationResponse> getApplications() {
        log.info("Fetching all product applications");

        List<ProductApplicationResponse> applications = productApplicationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

        log.info("Fetched {} product applications", applications.size());
        return applications;
    }

    public ProductApplicationResponse getApplicationById(Long id) {
        log.info("Fetching product application with id: {}", id);

        ProductApplication application = productApplicationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product application not found with id: {}", id);
                    return new ResourceNotFoundException("Product application not found with id: " + id);
                });

        return mapToResponse(application);
    }

    public ProductApplicationResponse createApplication(CreateProductApplicationRequest request) {
        Long customerId = request.getCustomerId();
        Long productId = request.getProductId();

        log.info("Creating product application for customer id: {} and product id: {}", customerId, productId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    log.warn("Cannot create product application. Customer not found with id: {}", customerId);
                    return new ResourceNotFoundException("Customer not found with id: " + customerId);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Cannot create product application. Product not found with id: {}", productId);
                    return new ResourceNotFoundException("Product not found with id: " + productId);
                });

        ProductApplication application = new ProductApplication();
        application.setStatus(request.getStatus());
        application.setCustomer(customer);
        application.setProduct(product);
        application.setCreatedDate(LocalDateTime.now());

        ProductApplication savedApplication = productApplicationRepository.save(application);

        log.info("Created product application with id: {}", savedApplication.getId());
        return mapToResponse(savedApplication);
    }

    private ProductApplicationResponse mapToResponse(ProductApplication application) {
        return ProductApplicationResponse.builder()
                .id(application.getId())
                .status(application.getStatus())
                .createdDate(application.getCreatedDate())
                .customer(customerService.mapToResponse(application.getCustomer()))
                .product(productService.mapToResponse(application.getProduct()))
                .build();
    }

    public ProductEligibilityResponse checkEligibility(Long customerId, Long productId) {

        log.info("Checking eligibility for customer id: {} and product id: {}", customerId, productId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    log.warn("Eligibility check failed. Customer not found with id: {}", customerId);
                    return new ResourceNotFoundException("Customer not found with id: " + customerId);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Eligibility check failed. Product not found with id: {}", productId);
                    return new ResourceNotFoundException("Product not found with id: " + productId);
                });

        List<String> failedChecks = new ArrayList<>();

        if (!product.getActive()) {
            failedChecks.add("Product is not active");
        }

        boolean customerTypeMatches = product.getQualifyingCustomerTypes()
                .contains(customer.getCustomerType());

        if (!customerTypeMatches) {
            failedChecks.add(
                    "Customer type must be one of: " +
                            product.getQualifyingCustomerTypes()
            );
        }

        boolean accountMatches = customer.getAccountTypes()
                .stream()
                .anyMatch(accountType ->
                        product.getQualifyingAccountTypes().contains(accountType)
                );

        if (!accountMatches) {
            failedChecks.add(
                    "Customer requires one of these accounts: " +
                            product.getQualifyingAccountTypes()
            );
        }

        if (!failedChecks.isEmpty()) {

            log.info("Customer id: {} is NOT eligible for product id: {}. Failed checks: {}",
                    customerId,
                    productId,
                    failedChecks
            );

            return ProductEligibilityResponse.builder()
                    .eligible(false)
                    .reason("Customer does not qualify for this product")
                    .failedChecks(failedChecks)
                    .build();
        }

        log.info("Customer id: {} is eligible for product id: {}", customerId, productId);

        return ProductEligibilityResponse.builder()
                .eligible(true)
                .reason("Customer qualifies for product")
                .failedChecks(List.of())
                .build();
    }
}