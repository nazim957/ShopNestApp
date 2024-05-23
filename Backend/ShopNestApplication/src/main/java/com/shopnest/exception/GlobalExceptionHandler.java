package com.shopnest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Error Occurred!!");
        errors.put("message", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductAlreadyExists.class)
    public ResponseEntity<Map<String, String>> handleProductAlreadyExistException(ProductAlreadyExists productAlreadyExists) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error while saving !!");
        errorResponse.put("message", productAlreadyExists.getCustomMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductNotExists.class)
    public ResponseEntity<Map<String, String>> handleProductAlreadyExistException(ProductNotExists productNotExists) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error while fetching !!");
        errorResponse.put("message", productNotExists.getCustomMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductCategoryNotFound.class)
    public ResponseEntity<Map<String, String>> handleProductCategoryNotExistException(ProductCategoryNotFound productCategoryNotFound) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error while getting Product category from server!!");
        errorResponse.put("message", productCategoryNotFound.getCustomMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, String>> handleJWTTokenException(InvalidTokenException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error Token is Required for Authentication: Unauthorized!!");
        errorResponse.put("message", "Invalid Token or the Token has been Expired!!");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
