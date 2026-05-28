package za.co.entelect.devcamp.productshopservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import za.co.entelect.devcamp.productshopservice.model.enums.Role;

@Data
public class UpdateCustomerRoleRequest {

    @NotNull(message = "Role is required")
    private Role role;
}