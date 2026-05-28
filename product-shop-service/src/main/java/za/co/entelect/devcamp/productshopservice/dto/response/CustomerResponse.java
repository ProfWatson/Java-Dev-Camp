package za.co.entelect.devcamp.productshopservice.dto.response;

import lombok.Builder;
import lombok.Data;
import za.co.entelect.devcamp.productshopservice.model.enums.AccountType;
import za.co.entelect.devcamp.productshopservice.model.enums.CustomerType;
import za.co.entelect.devcamp.productshopservice.model.enums.Role;

import java.util.Set;

@Data
@Builder
public class CustomerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String idNumber;
    private String phoneNumber;
    private CustomerType customerType;
    private Set<AccountType> accountTypes;
    private Role role;
}