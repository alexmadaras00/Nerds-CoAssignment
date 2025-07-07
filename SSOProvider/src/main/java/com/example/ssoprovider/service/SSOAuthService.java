package com.example.ssoprovider.service;

import com.example.ssoprovider.dto.ChangePasswordDto;
import com.example.ssoprovider.dto.UserLoginDto;
import com.example.ssoprovider.dto.UserRegisterDto;
import com.example.ssoprovider.dto.UserResponseDto;

public interface SSOAuthService {
    UserResponseDto register(UserRegisterDto userRegistering);
    UserResponseDto login(UserLoginDto userLogin);
    void changePassword(ChangePasswordDto changePasswordDto);
    void logout(String token);
}
