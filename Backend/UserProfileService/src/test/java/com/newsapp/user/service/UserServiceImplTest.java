//package com.newsapp.user.service;
//import com.newsapp.user.config.Producer;
//import com.newsapp.user.exception.UserAlreadyExistException;
//import com.newsapp.user.model.Role;
//import com.newsapp.user.model.User;
//import com.newsapp.user.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.Spy;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class UserServiceImplTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private Producer producer;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Spy
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testRegisterUserSuccess() throws UserAlreadyExistException {
//        // Arrange
//        User user = new User("test@example.com", "testUser", "password", "1234567890", Role.USER);
//
//        when(userRepository.findByEmail(any())).thenReturn(null);
//        when(userRepository.save(any())).thenReturn(user);
//
//        // Mock the Producer
//        Producer mockProducer = mock(Producer.class);
//        doNothing().when(mockProducer).sendMessageToRabbitMq(any());
//
//        // Create UserServiceImpl instance with mocks
//        UserServiceImpl userService = new UserServiceImpl(userRepository, mockProducer, passwordEncoder);
//
//        // Act
//        User savedUser = userService.registerUser(user);
//
//        // Assert
//        assertNotNull(savedUser);
//        assertEquals(user.getEmail(), savedUser.getEmail());
//        verify(userRepository, times(1)).save(any());
//        verify(mockProducer, times(1)).sendMessageToRabbitMq(any());
//    }
//
//
//    @Test
//    void testRegisterUserFailureUserAlreadyExist() {
//        // Arrange
//        User user = new User("existing@example.com", "existingUser", "password", "1234567890", Role.USER);
//
//        when(userRepository.findByEmail(any())).thenReturn(user);
//
//        // Act and Assert
//        assertThrows(UserAlreadyExistException.class, () -> userService.registerUser(user));
//        verify(userRepository, never()).save(any());
//        verify(producer, never()).sendMessageToRabbitMq(any());
//    }
//
//
//    @Test
//    void testRegisterUserFailureEmailEmpty() {
//        // Arrange
//        User user = new User("", "testUser", "password", "1234567890", Role.USER);
//
//        // Mock the findByEmail method to return null
//        when(userRepository.findByEmail(any())).thenReturn(null);
//
//        // Act and Assert
//        assertThrows(UserAlreadyExistException.class, () -> userService.registerUser(user));
//        verify(userRepository, never()).save(any());
//        verify(producer, never()).sendMessageToRabbitMq(any());
//    }
//
//}
