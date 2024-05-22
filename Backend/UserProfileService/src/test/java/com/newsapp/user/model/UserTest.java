package com.newsapp.user.model;

import com.newsapp.user.model.Role;
import com.newsapp.user.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testCreateUserSuccess() {
        // Arrange
        String email = "test@example.com";
        String userName = "testUser";
        String password = "password";
        String phoneNumber = "1234567890";
        Role role = Role.USER;

        // Act
        User user = new User(email, userName, password, phoneNumber, role);

        // Assert
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(userName, user.getUserName());
        assertEquals(password, user.getPassword());
        assertEquals(phoneNumber, user.getPhoneNumber());
        assertEquals(role, user.getRole());
    }


}
