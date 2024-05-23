package com.onlineshoppy.controller;


import com.onlineshoppy.exception.InvalidTokenException;
import com.onlineshoppy.model.Order;
import com.onlineshoppy.repository.CustomerRepository;
import com.onlineshoppy.repository.OrderRepository;
import com.onlineshoppy.security.JWTTokenValidator;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/orders")
public class OrderController {


    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final JWTTokenValidator jwtTokenValidator;

    @Autowired
    public OrderController(OrderRepository orderRepository, CustomerRepository customerRepository, JWTTokenValidator jwtTokenValidator) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.jwtTokenValidator = jwtTokenValidator;
    }

    @GetMapping("/search/findByCustomerEmail")
    public ResponseEntity<?> getOrdersByCustomerEmail(@Parameter(hidden = true) @RequestHeader("Authorization") String token) throws InvalidTokenException {
        validateToken(token);
        String emailId = jwtTokenValidator.extractUserIdFromToken(token);
        List<Order> byCustomerEmailOrderByDateCreatedDesc = orderRepository.findByCustomerEmailOrderByDateCreatedDesc(emailId);
        if(byCustomerEmailOrderByDateCreatedDesc!=null) {
         //   return new ResponseEntity<>(byCustomerEmailOrderByDateCreatedDesc, HttpStatus.OK);
            return ResponseEntity.ok(byCustomerEmailOrderByDateCreatedDesc);
        }
        return new ResponseEntity<>("No order Found",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void validateToken(String token) throws InvalidTokenException {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new InvalidTokenException("Missing or Invalid Token");
        }

        // Validate the JWT token
        Map<String, String> validationMap = jwtTokenValidator.validateToken(token.substring(7)); // Remove "Bearer " prefix

        // Check if the token is valid
        if (validationMap.containsKey("error")) {
            throw new InvalidTokenException("Invalid Token");
        }
    }
}
