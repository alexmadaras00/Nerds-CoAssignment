package com.example.ssoprovider.controller;

import com.example.ssoprovider.dto.ChangePasswordDto;
import com.example.ssoprovider.dto.UserLoginDto;
import com.example.ssoprovider.dto.UserRegisterDto;
import com.example.ssoprovider.dto.UserResponseDto;
import com.example.ssoprovider.service.SSOAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Better Mockito integration with JUnit 5
class SSOAuthControllerTest {

    @InjectMocks
    private SSOAuthController ssoAuthController;

    @Mock
    private SSOAuthService ssoAuthService;

    @Test
    void testRegister() {
        UserRegisterDto registerDto = new UserRegisterDto("Alex", "alex@example.com", "pass123", Date.valueOf("2000-11-30")); // corrected date format
        UserResponseDto expectedResponse = new UserResponseDto(true, "alex@example.com", "token");

        when(ssoAuthService.register(registerDto)).thenReturn(expectedResponse);

        ResponseEntity<UserResponseDto> response = ssoAuthController.register(registerDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testLogin() {
        UserLoginDto loginDto = new UserLoginDto("alex@example.com", "pass123");
        UserResponseDto expectedResponse = new UserResponseDto(true, "alex@example.com", "token");

        when(ssoAuthService.login(loginDto)).thenReturn(expectedResponse);

        ResponseEntity<UserResponseDto> response = ssoAuthController.login(loginDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testChangePassword() {
        String email = "alex@example.com";
        ChangePasswordDto passwordDto = new ChangePasswordDto("oldPass", "newPass");
        UserResponseDto expectedResponse = new UserResponseDto(true, email, null);

        when(ssoAuthService.changePassword(email, passwordDto)).thenReturn(expectedResponse);

        ResponseEntity<UserResponseDto> response = ssoAuthController.changePassword(email, passwordDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testLogout() {
        String token = "sample.jwt.token";
        String header = "Bearer " + token;
        UserResponseDto expectedResponse = new UserResponseDto(true, null, null);

        when(ssoAuthService.logout(token)).thenReturn(expectedResponse);

        ResponseEntity<UserResponseDto> response = ssoAuthController.logout(header);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}
