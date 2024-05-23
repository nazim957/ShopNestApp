//package com.newsapp.auth.service;
//
//import com.newsapp.auth.exception.InvalidCredentialsException;
//import com.newsapp.auth.exception.UserAlreadyExistException;
//import com.newsapp.auth.model.User;
//import com.newsapp.auth.repository.AuthenticationRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class AuthServiceImplTest {
//
//    @Mock
//    private AuthenticationRepository authenticationRepository;
//
//    @InjectMocks
//    private AuthServiceImpl authService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void saveCredentials_NewUser_Success() throws UserAlreadyExistException {
//        // Arrange
//        User newUser = new User("newuser@example.com", "password", "USER");
//
//        when(authenticationRepository.findById(newUser.getEmail())).thenReturn(java.util.Optional.empty());
//        when(authenticationRepository.save(any(User.class))).thenReturn(newUser);
//
//        // Act
//        User savedUser = authService.saveCredentials(newUser);
//
//        // Assert
//        assertNotNull(savedUser);
//        assertEquals(newUser.getEmail(), savedUser.getEmail());
//        assertEquals(newUser.getPassword(), savedUser.getPassword());
//        assertEquals(newUser.getRole(), savedUser.getRole());
//
//        // Verify repository methods were called
//        verify(authenticationRepository, times(1)).findById(newUser.getEmail());
//        verify(authenticationRepository, times(1)).save(any(User.class));
//    }
//
//    @Test
//    void saveCredentials_ExistingUser_ThrowsException() {
//        // Arrange
//        User existingUser = new User("existinguser@example.com", "password", "USER");
//
//        when(authenticationRepository.findById(existingUser.getEmail())).thenReturn(java.util.Optional.of(existingUser));
//
//        // Act and Assert
//        assertThrows(UserAlreadyExistException.class, () -> authService.saveCredentials(existingUser));
//
//        // Verify repository methods were called
//        verify(authenticationRepository, times(1)).findById(existingUser.getEmail());
//        verify(authenticationRepository, never()).save(any(User.class));
//    }
//
//    @Test
//    void findByEmailAndPassword_ValidCredentials_Success() throws InvalidCredentialsException {
//        // Arrange
//        String email = "validuser@example.com";
//        String password = "password";
//        User user = new User(email, password, "USER");
//
//        when(authenticationRepository.findByEmailAndPassword(email, password)).thenReturn(user);
//
//        // Act
//        User loggedInUser = authService.findByEmailAndPassword(email, password);
//
//        // Assert
//        assertNotNull(loggedInUser);
//        assertEquals(user.getEmail(), loggedInUser.getEmail());
//        assertEquals(user.getPassword(), loggedInUser.getPassword());
//        assertEquals(user.getRole(), loggedInUser.getRole());
//
//        // Verify repository method was called
//        verify(authenticationRepository, times(1)).findByEmailAndPassword(email, password);
//    }
//
//    @Test
//    void findByEmailAndPassword_InvalidCredentials_ThrowsException() {
//        // Arrange
//        String email = "invaliduser@example.com";
//        String password = "wrongpassword";
//
//        when(authenticationRepository.findByEmailAndPassword(email, password)).thenReturn(null);
//
//        // Act and Assert
//        assertThrows(InvalidCredentialsException.class, () -> authService.findByEmailAndPassword(email, password));
//
//        // Verify repository method was called
//        verify(authenticationRepository, times(1)).findByEmailAndPassword(email, password);
//    }
//}
