package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void createUserDTO_WithValues_Success() {
        // Arrange
        String email = "test@example.com";
        String userName = "testuser";
        String password = "password";
        String role = "USER";

        // Act
        UserDTO userDTO = new UserDTO(email, userName, password, role);

        // Assert
        assertNotNull(userDTO);
        assertEquals(email, userDTO.getEmail());
        assertEquals(userName, userDTO.getUserName());
        assertEquals(password, userDTO.getPassword());
        assertEquals(role, userDTO.getRole());
    }

    @Test
    void createUserDTO_DefaultConstructor_Success() {
        // Act
        UserDTO userDTO = new UserDTO();

        // Assert
        assertNotNull(userDTO);
        assertNull(userDTO.getEmail());
        assertNull(userDTO.getUserName());
        assertNull(userDTO.getPassword());
        assertNull(userDTO.getRole());
    }

    @Test
    void settersAndGetters_WorkAsExpected() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        String email = "test@example.com";
        String userName = "testuser";
        String password = "password";
        String role = "USER";

        // Act
        userDTO.setEmail(email);
        userDTO.setUserName(userName);
        userDTO.setPassword(password);
        userDTO.setRole(role);

        // Assert
        assertEquals(email, userDTO.getEmail());
        assertEquals(userName, userDTO.getUserName());
        assertEquals(password, userDTO.getPassword());
        assertEquals(role, userDTO.getRole());
    }
}
