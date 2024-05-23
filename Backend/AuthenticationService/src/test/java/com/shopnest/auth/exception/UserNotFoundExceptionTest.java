//package com.newsapp.auth.exception;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserNotFoundExceptionTest {
//
//    @Test
//    void userNotFoundException_WithMessage_Success() {
//        // Arrange
//        String errorMessage = "User not found";
//
//        // Act
//        UserNotFoundException exception = new UserNotFoundException(errorMessage);
//
//        // Assert
//        assertNotNull(exception);
//        assertEquals(errorMessage, exception.getMessage());
//    }
//
//    @Test
//    void userNotFoundException_WithoutMessage_Success() {
//        // Act
//        UserNotFoundException exception = new UserNotFoundException();
//
//        // Assert
//        assertNotNull(exception);
//        assertNull(exception.getMessage());
//    }
//}
