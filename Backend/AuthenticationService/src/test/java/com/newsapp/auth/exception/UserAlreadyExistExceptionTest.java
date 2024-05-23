//package com.newsapp.auth.exception;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserAlreadyExistExceptionTest {
//
//    @Test
//    void userAlreadyExistException_WithMessage_Success() {
//        // Arrange
//        String errorMessage = "User already exists";
//
//        // Act
//        UserAlreadyExistException exception = new UserAlreadyExistException(errorMessage);
//
//        // Assert
//        assertNotNull(exception);
//        assertEquals(errorMessage, exception.getMessage());
//    }
//
//    @Test
//    void userAlreadyExistException_WithoutMessage_Success() {
//        // Act
//        UserAlreadyExistException exception = new UserAlreadyExistException();
//
//        // Assert
//        assertNotNull(exception);
//        assertNull(exception.getMessage());
//    }
//}
