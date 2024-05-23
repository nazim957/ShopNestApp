package com.newsapp.auth.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void createUser_Success() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        String role = "USER";

        // Act
        User user = new User(email, password, role);

        // Assert
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }

    @Test
    void gettersAndSetters_WorkAsExpected() {
        // Arrange
        User user = new User();
        String email = "test@example.com";
        String password = "password";
        String role = "USER";

        // Act
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        // Assert
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }

    @Test
    void toString_ReturnsExpectedString() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        String role = "USER";
        User user = new User(email, password, role);

        // Act
        String toStringResult = user.toString();

        // Assert
        assertTrue(toStringResult.contains(email));
        assertTrue(toStringResult.contains(password));
        assertTrue(toStringResult.contains(role));
    }
}
