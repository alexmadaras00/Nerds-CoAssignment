package com.example.ssoprovider.service;

import com.example.ssoprovider.dto.ChangePasswordDto;
import com.example.ssoprovider.dto.UserLoginDto;
import com.example.ssoprovider.dto.UserRegisterDto;
import com.example.ssoprovider.dto.UserResponseDto;
import com.example.ssoprovider.model.UserUpdateDto;

import java.util.List;

public interface SSOAuthService {
    UserResponseDto register(UserRegisterDto userRegistering);
    UserResponseDto login(UserLoginDto userLogin);
    UserResponseDto changePassword(String userId, ChangePasswordDto changePasswordDto);
    UserResponseDto logout(String token);

    List<UserResponseDto> getUserData();
    UserResponseDto updateUser(String userId, UserUpdateDto userUpdateDto);
    void deleteUser(String token);
}
