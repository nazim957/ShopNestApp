package com.newsapp.auth.exception;

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

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistException(InvalidCredentialsException invalidCredentialsException) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error while Logging In User!!");
        errorResponse.put("message", invalidCredentialsException.getCustomMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<Map<String, String>> handleUserNotVerifiedException(UserNotVerifiedException userNotVerifiedException    ) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error while Logging In User!!");
        errorResponse.put("message", userNotVerifiedException.getCustomMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}
