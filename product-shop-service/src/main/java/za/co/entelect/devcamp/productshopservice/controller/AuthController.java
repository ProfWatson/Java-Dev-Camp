package za.co.entelect.devcamp.productshopservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import za.co.entelect.devcamp.productshopservice.dto.request.LoginRequest;
import za.co.entelect.devcamp.productshopservice.dto.request.RefreshTokenRequest;
import za.co.entelect.devcamp.productshopservice.dto.response.LoginResponse;
import za.co.entelect.devcamp.productshopservice.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public LoginResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }
}