package com.newsapp.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistException(UserAlreadyExistException userAlreadyExistException) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error while Registering User!!");
        errorResponse.put("message", userAlreadyExistException.getCustomMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SecurityMismatchException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistException(SecurityMismatchException securityMismatchException) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error while updating User!!");
        errorResponse.put("message", securityMismatchException.getCustomMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotExistException(UserNotFoundException userNotFoundException) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error while fetching User from server.!!");
        errorResponse.put("message", userNotFoundException.getCustomMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<Map<String, String>> handleUserNotVerifiedException(UserNotVerifiedException userNotVerifiedException    ) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error while Updating User!!");
        errorResponse.put("message", userNotVerifiedException.getCustomMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}
