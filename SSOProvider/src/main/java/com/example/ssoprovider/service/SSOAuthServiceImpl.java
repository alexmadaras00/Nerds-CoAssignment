package com.example.ssoprovider.service;

import com.example.ssoprovider.dto.ChangePasswordDto;
import com.example.ssoprovider.dto.UserLoginDto;
import com.example.ssoprovider.dto.UserRegisterDto;
import com.example.ssoprovider.dto.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public class SSOAuthServiceImpl implements SSOAuthService{
    @Override
    public UserResponseDto register(UserRegisterDto userRegistering) {
        return null;
    }

    @Override
    public UserResponseDto login(UserLoginDto userLogin) {
        return null;
    }

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto) {

    }

    @Override
    public void logout(String token) {

    }


}
