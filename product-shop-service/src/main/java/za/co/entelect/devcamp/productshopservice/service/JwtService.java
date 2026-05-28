package za.co.entelect.devcamp.productshopservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import za.co.entelect.devcamp.productshopservice.model.Customer;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-seconds}")
    private Long expirationSeconds;

    public String generateToken(Customer customer) {

        Date now = new Date();

        Date expiryDate = new Date(
                now.getTime() + (expirationSeconds * 1000)
        );

        SecretKey key = Keys.hmacShaKeyFor(
                jwtSecret.getBytes(StandardCharsets.UTF_8)
        );

        return Jwts.builder()
                .setSubject(customer.getEmail())
                .claim("customerId", customer.getId())
                .claim("role", customer.getRole().name())
                .setIssuer("product-shop-service")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Long extractCustomerId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("customerId", Long.class);
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public String generateRefreshToken(Customer customer) {

        Date now = new Date();

        Date expiryDate = new Date(
                now.getTime() + (7 * 24 * 60 * 60 * 1000L)
        );

        return Jwts.builder()
                .setSubject(customer.getEmail())
                .claim("customerId", customer.getId())
                .claim("role", customer.getRole().name())
                .setIssuer("product-shop-service")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}