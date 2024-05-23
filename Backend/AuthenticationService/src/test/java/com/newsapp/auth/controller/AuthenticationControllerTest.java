package com.newsapp.auth.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsapp.auth.exception.InvalidCredentialsException;
import com.newsapp.auth.exception.UserAlreadyExistException;
import com.newsapp.auth.model.User;
import com.newsapp.auth.model.UserCredentials;
import com.newsapp.auth.security.JWTTokenGenerator;
import com.newsapp.auth.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthenticationControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private JWTTokenGenerator jwtTokenGenerator;

    @InjectMocks
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    void saveCredentials_Success() throws Exception {
        User user = new User("test@example.com", "password", "USER");

        when(authService.saveCredentials(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/v2/saveCredentials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.password").value(user.getPassword()))  // Adjusted expectation
                .andExpect(jsonPath("$.role").value(user.getRole()));

    }

//    @Test
//    void saveCredentials_UserAlreadyExists() throws Exception {
//        User user = new User("test@example.com", "password", "USER");
//
//        when(authService.saveCredentials(any(User.class))).thenThrow(new UserAlreadyExistException("User already exists"));
//
//        MvcResult result = mockMvc.perform(post("/api/v2/saveCredentials")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(user)))
//                .andExpect(status().isConflict())
//                .andReturn();
//
//        // Extract and assert the exception
//        Exception resolvedException = result.getResolvedException();
//        assertNotNull(resolvedException);
//        assertTrue(resolvedException instanceof NestedServletException);
//
//        Throwable rootCause = resolvedException.getCause();
//        assertNotNull(rootCause);
//        assertTrue(rootCause instanceof UserAlreadyExistException);
//        assertEquals("User already exists", rootCause.getMessage());
//    }
//




    @Test
    void loginUser_Success() throws Exception {
        UserCredentials userCredentials = new UserCredentials("test@example.com", "password");
        User user = new User("test@example.com", "password", "USER");

        when(authService.findByEmailAndPassword(userCredentials.getEmail(), userCredentials.getPassword())).thenReturn(user);
        when(jwtTokenGenerator.generateToken(user)).thenReturn(Map.of("token", "mockedToken"));

        mockMvc.perform(post("/api/v2/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCredentials)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockedToken"));
    }
//    @Test
//    void loginUser_InvalidCredentials() throws Exception {
//        UserCredentials userCredentials = new UserCredentials("test@example.com", "wrongpassword");
//
//        when(authService.findByEmailAndPassword(userCredentials.getEmail(), userCredentials.getPassword())).thenThrow(new InvalidCredentialsException());
//
//        mockMvc.perform(post("/api/v2/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userCredentials)))
//                .andExpect(status().isUnauthorized())
//                .andExpect(jsonPath("$.error").value("Invalid credentials"));
//    }


}

