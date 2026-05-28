package za.co.entelect.devcamp.productshopservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import za.co.entelect.devcamp.productshopservice.model.enums.AccountType;
import za.co.entelect.devcamp.productshopservice.model.enums.CustomerType;
import za.co.entelect.devcamp.productshopservice.model.enums.Role;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "ID number is required")
    private String idNumber;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Customer type is required")
    private CustomerType customerType;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountType> accountTypes = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role is required")
    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}