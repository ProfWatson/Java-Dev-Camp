package za.co.entelect.devcamp.productshopservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import za.co.entelect.devcamp.productshopservice.dto.request.CreateCustomerRequest;
import za.co.entelect.devcamp.productshopservice.dto.response.CustomerResponse;
import za.co.entelect.devcamp.productshopservice.exception.ResourceNotFoundException;
import za.co.entelect.devcamp.productshopservice.model.Customer;
import za.co.entelect.devcamp.productshopservice.repository.CustomerRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<CustomerResponse> getCustomers() {
        log.info("Fetching all customers");

        List<CustomerResponse> customers = customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

        log.info("Fetched {} customers", customers.size());
        return customers;
    }

    public CustomerResponse getCustomerById(Long id) {
        log.info("Fetching customer with id: {}", id);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Customer not found with id: {}", id);
                    return new ResourceNotFoundException("Customer not found with id: " + id);
                });

        return mapToResponse(customer);
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        log.info("Creating customer with email: {}", request.getEmail());

        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setIdNumber(request.getIdNumber());
        customer.setPhoneNumber(request.getPhoneNumber());

        Customer savedCustomer = customerRepository.save(customer);

        log.info("Created customer with id: {}", savedCustomer.getId());
        return mapToResponse(savedCustomer);
    }

    public CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .idNumber(customer.getIdNumber())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }
}