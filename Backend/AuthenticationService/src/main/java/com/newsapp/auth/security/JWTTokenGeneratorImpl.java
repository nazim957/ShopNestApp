package com.newsapp.auth.security;

import com.newsapp.auth.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * This class is implementing the JWTTokenGenerator interface. This class has to be annotated with
 * @Service annotation.
 * @Service indicates annotated class is a service
 * which hold business logic in the Service layer
 *
 * */

@Service
public class JWTTokenGeneratorImpl implements JWTTokenGenerator {

    /**
     * To get the property values
     */
     Logger logger = LoggerFactory.getLogger(JWTTokenGeneratorImpl.class);
    @Value("${jwt.secret}")
    private String secret;

    @Value("${app.jwttoken.message}")
    private String message;

    @Override
    public Map<String, String> generateToken(User user) {
        String jwtToken = "";
        /*
         * Generate JWT token and store in String jwtToken
         */

        Map<String, String> jwtTokenMap = new HashMap<>();

        jwtToken = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())  // Assuming user.getRole() returns "ADMIN" or "USER"
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3000000))
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();

        jwtTokenMap.put("message","Login Successful");
        if(user.getRole().equals("ADMIN")){
            jwtTokenMap.put("Admin","Hello ADMIN HOW ARE U??");
            jwtTokenMap.put("userName", user.getUserName());
            jwtTokenMap.put("role", user.getRole());
            jwtTokenMap.put("email", user.getEmail());
        }

        else if(user.getRole().equals("USER")) {
            jwtTokenMap.put("User", "Hello USER HOW ARE U??");
            jwtTokenMap.put("userName", user.getUserName());
            jwtTokenMap.put("role", user.getRole());
            jwtTokenMap.put("email", user.getEmail());
        }
        jwtTokenMap.put("token",jwtToken);

        return jwtTokenMap;
    }
}
