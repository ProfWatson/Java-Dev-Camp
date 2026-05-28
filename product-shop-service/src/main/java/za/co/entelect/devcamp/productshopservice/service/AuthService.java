package za.co.entelect.devcamp.productshopservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.entelect.devcamp.productshopservice.dto.request.LoginRequest;
import za.co.entelect.devcamp.productshopservice.dto.request.RefreshTokenRequest;
import za.co.entelect.devcamp.productshopservice.dto.response.LoginResponse;
import za.co.entelect.devcamp.productshopservice.exception.AuthenticationFailedException;
import za.co.entelect.devcamp.productshopservice.model.Customer;
import za.co.entelect.devcamp.productshopservice.repository.CustomerRepository;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());

        Customer customer = customerRepository.findFirstByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("Login failed. Customer not found for email: {}", request.getEmail());
                    return new AuthenticationFailedException("Invalid email or password");
                });

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), customer.getPassword());

        if (!passwordMatches) {
            log.warn("Login failed. Invalid password for email: {}", request.getEmail());
            throw new AuthenticationFailedException("Invalid email or password");
        }

        log.info("Login successful for customer id: {}", customer.getId());

        return LoginResponse.builder()
                .accessToken(jwtService.generateToken(customer))
                .refreshToken(jwtService.generateRefreshToken(customer))
                .tokenType("Bearer")
                .build();
    }

    public LoginResponse refreshToken(RefreshTokenRequest request) {

        String refreshToken = request.getRefreshToken();

        if (!jwtService.isTokenValid(refreshToken)) {
            throw new AuthenticationFailedException("Invalid refresh token");
        }

        String email = jwtService.extractEmail(refreshToken);

        Customer customer = customerRepository.findFirstByEmailIgnoreCase(email)
                .orElseThrow(() -> new AuthenticationFailedException("Invalid refresh token"));

        return LoginResponse.builder()
                .accessToken(jwtService.generateToken(customer))
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }
}