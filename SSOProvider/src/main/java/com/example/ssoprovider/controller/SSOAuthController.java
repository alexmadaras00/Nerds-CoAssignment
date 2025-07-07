package com.example.ssoprovider.controller;

import com.example.ssoprovider.dto.ChangePasswordDto;
import com.example.ssoprovider.dto.UserLoginDto;
import com.example.ssoprovider.dto.UserRegisterDto;
import com.example.ssoprovider.dto.UserResponseDto;
import com.example.ssoprovider.service.SSOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class SSOAuthController {

    @Autowired
    private SSOAuthService ssoAuthService;



    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity.ok(ssoAuthService.register(userRegisterDto));
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok(ssoAuthService.login(userLoginDto));
    }
    @PostMapping("/change-password")
    public ResponseEntity<UserResponseDto> changePassword(@RequestParam String email, @RequestBody ChangePasswordDto changePasswordDto) {
        return ResponseEntity.ok(ssoAuthService.changePassword(email, changePasswordDto));
    }
    @PostMapping("/logout")
    public ResponseEntity<UserResponseDto> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(ssoAuthService.logout(token));
    }

}
