package com.lucasnvs.trilho.auth.controllers;

import com.lucasnvs.trilho.auth.domain.user.User;
import com.lucasnvs.trilho.auth.dto.LoginRequestDTO;
import com.lucasnvs.trilho.auth.dto.RegisterRequestDTO;
import com.lucasnvs.trilho.auth.dto.ResponseDTO;
import com.lucasnvs.trilho.auth.repositories.UserRepository;
import com.lucasnvs.trilho.auth.infra.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setName("Test User");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(tokenService.generateToken(any(User.class))).thenReturn("token");
    }

    @Test
    void testLogin() {
        LoginRequestDTO loginRequest = new LoginRequestDTO("test@example.com", "password");

        ResponseEntity<ResponseDTO> response = authController.login(loginRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test User", response.getBody().name());
        assertEquals("token", response.getBody().token());
    }

    @Test
    void testRegister() {
        RegisterRequestDTO registerRequest = new RegisterRequestDTO("Test User", "test@example.com", "password");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        ResponseEntity<ResponseDTO> response = authController.register(registerRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test User", response.getBody().name());
        assertEquals("token", response.getBody().token());
    }
}