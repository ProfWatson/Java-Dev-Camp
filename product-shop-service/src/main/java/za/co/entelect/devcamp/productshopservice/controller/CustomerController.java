package za.co.entelect.devcamp.productshopservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import za.co.entelect.devcamp.productshopservice.dto.request.CreateCustomerRequest;
import za.co.entelect.devcamp.productshopservice.dto.request.UpdateCustomerRoleRequest;
import za.co.entelect.devcamp.productshopservice.dto.response.CustomerResponse;
import za.co.entelect.devcamp.productshopservice.model.Customer;
import za.co.entelect.devcamp.productshopservice.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerResponse> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public CustomerResponse createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        return customerService.createCustomer(request);
    }

    @PatchMapping("/{id}/role")
    public CustomerResponse updateCustomerRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerRoleRequest request
    ) {
        return customerService.updateCustomerRole(id, request.getRole());
    }
}