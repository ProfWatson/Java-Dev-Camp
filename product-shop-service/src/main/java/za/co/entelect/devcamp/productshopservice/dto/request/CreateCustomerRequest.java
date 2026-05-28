package za.co.entelect.devcamp.productshopservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import za.co.entelect.devcamp.productshopservice.model.enums.AccountType;
import za.co.entelect.devcamp.productshopservice.model.enums.CustomerType;
import za.co.entelect.devcamp.productshopservice.model.enums.Role;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreateCustomerRequest {

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

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Customer type is required")
    private CustomerType customerType;

    private Set<AccountType> accountTypes = new HashSet<>();

    private Role role = Role.CUSTOMER;
}