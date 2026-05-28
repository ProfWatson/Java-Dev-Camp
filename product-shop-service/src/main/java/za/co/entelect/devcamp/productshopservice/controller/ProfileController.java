package za.co.entelect.devcamp.productshopservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.entelect.devcamp.productshopservice.dto.response.CustomerResponse;
import za.co.entelect.devcamp.productshopservice.service.CustomerService;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {

    private final CustomerService customerService;

    @GetMapping("/me")
    public CustomerResponse getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        return customerService.getCustomerByEmail(email);
    }
}