package com.shopnest.controller;

import com.shopnest.dto.Purchase;
import com.shopnest.dto.PurchaseResponse;
import com.shopnest.exception.InvalidTokenException;
import com.shopnest.security.JWTTokenValidator;
import com.shopnest.service.CheckoutService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
@CrossOrigin("*")
public class CheckoutController {


    private final CheckoutService checkoutService;
    private final JWTTokenValidator jwtTokenValidator;

    @Autowired
    public CheckoutController(CheckoutService checkoutService, JWTTokenValidator jwtTokenValidator) {
        this.checkoutService = checkoutService;
        this.jwtTokenValidator=jwtTokenValidator;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> placeOrder( @Parameter(hidden = true) @RequestHeader("Authorization") String token,
                                         @RequestBody Purchase purchase) throws InvalidTokenException {

        validateToken(token);
        // Extract user ID from the JWT token
        String userId = jwtTokenValidator.extractUserIdFromToken(token);
    //    System.out.println("UserID%%%%"+ userId);
        // Set the user ID in the purchase object
        purchase.getCustomer().setEmail(userId);

        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);
        if(purchaseResponse!=null) {
            return new ResponseEntity<>(purchaseResponse, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Error in Adding Order", HttpStatus.INTERNAL_SERVER_ERROR);
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
