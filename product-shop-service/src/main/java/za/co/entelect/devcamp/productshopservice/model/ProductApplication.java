package za.co.entelect.devcamp.productshopservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import za.co.entelect.devcamp.productshopservice.model.enums.ApplicationStatus;

import java.time.LocalDateTime;

@Data
@Entity
public class ProductApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Application status is required")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private LocalDateTime createdDate;

    @NotNull(message = "Customer is required")
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull(message = "Product is required")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}