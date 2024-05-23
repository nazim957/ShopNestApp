package com.onlineshoppy.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JWTTokenValidator {

    Logger logger = LoggerFactory.getLogger(JWTTokenValidator.class);

    @Value("${jwt.secret}")
    private String secret;

    public Map<String, String> validateToken(String token) {
        Map<String, String> validationMap = new HashMap<>();
        
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

            validationMap.put("message", "Token is valid");
            validationMap.put("userName", claims.getSubject());
            validationMap.put("role", claims.get("role", String.class));

            // You can add more validation or additional information from claims if needed

        } catch (Exception e) {
            // Token validation failed
            logger.error("Error validating JWT token: {}", e.getMessage());
            validationMap.put("error", "Invalid token");
        }

        return validationMap;
    }

    // Method to extract user email from the JWT token
    public String extractUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.substring(7)) // Remove "Bearer " prefix
                    .getBody();

            // Extract email from the subject
            return claims.getSubject();

        } catch (Exception e) {
            // Token validation failed
            logger.error("Error extracting user ID from JWT token: {}", e.getMessage());
            return null; // Return null or handle the error according to your needs
        }
    }
}
