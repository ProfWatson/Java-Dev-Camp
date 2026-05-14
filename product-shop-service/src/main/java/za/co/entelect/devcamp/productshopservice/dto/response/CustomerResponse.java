package za.co.entelect.devcamp.productshopservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String idNumber;
    private String phoneNumber;
}