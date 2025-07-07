package com.example.ssoprovider.service;

import com.example.ssoprovider.dto.ChangePasswordDto;
import com.example.ssoprovider.dto.UserLoginDto;
import com.example.ssoprovider.dto.UserRegisterDto;
import com.example.ssoprovider.dto.UserResponseDto;
import com.example.ssoprovider.jwt.JWTConfig;
import com.example.ssoprovider.model.User;
import com.example.ssoprovider.model.UserUpdateDto;
import com.example.ssoprovider.repository.SSOAuthRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SSOAuthServiceImpl implements SSOAuthService {

    @Setter
    @Getter
    private Set<String> tokenBlacklist = Collections.synchronizedSet(new HashSet<>());
    @Autowired
    SSOAuthRepository userRepository;

    @Autowired
    JWTConfig jwtConfig;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserResponseDto register(UserRegisterDto userRegistering) {
        User user = new User();
        user.setFullName(userRegistering.getFullName());
        user.setEmail(userRegistering.getEmail());
        user.setBirthDate(userRegistering.getDateOfBirth().toLocalDate());
        user.setPassword(userRegistering.getPassword());
        user.setPassword(passwordEncoder.encode(userRegistering.getPassword()));
        User savedUser = userRepository.save(user);
        UserResponseDto response = new UserResponseDto();

        String token = jwtConfig.generateToken(savedUser);

        response.setId(savedUser.getUserId());
        response.setEmail(savedUser.getEmail());
        response.setToken(token);
        return response;
    }

    @Override
    public UserResponseDto login(UserLoginDto userLogin) {
        UserResponseDto responseDto = new UserResponseDto();

        Optional<User> optionalUser = userRepository.findByEmail(userLogin.getEmail());

        if (optionalUser.isEmpty()) {
            responseDto.setSuccess(false);
            responseDto.setMessage("User not found");
            return responseDto;
        }

        User user = optionalUser.get();
        String token = jwtConfig.generateToken(user);
        if (!passwordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
            responseDto.setSuccess(false);
            responseDto.setMessage("Invalid credentials");
            return responseDto;
        }

        responseDto.setId(user.getUserId());
        responseDto.setEmail(user.getEmail());
        responseDto.setSuccess(true);
        responseDto.setToken(token);
        responseDto.setMessage("Successfully authenticated!");
        return responseDto;
    }


    @Override
    public UserResponseDto changePassword(String userId, ChangePasswordDto changePasswordDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        UserResponseDto response = new UserResponseDto();

        if (optionalUser.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("User not found");
            return response;
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            response.setSuccess(false);
            response.setMessage("Old password is incorrect");
            return response;
        }

        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);

        String token = jwtConfig.generateToken(user);

        response.setSuccess(true);
        response.setMessage("Successfully changed password");
        response.setId(user.getUserId());
        response.setEmail(user.getEmail());
        response.setToken(token);

        return response;
    }


    @Override
    public UserResponseDto logout(String token) {
        UserResponseDto responseDto = new UserResponseDto();
        if (tokenBlacklist.contains(token)) {
            responseDto.setSuccess(false);
            responseDto.setMessage("User already logged out!");
            return responseDto;
        }
        tokenBlacklist.add(token);
        responseDto.setSuccess(true);
        responseDto.setMessage("Successfully logged out!");
        return responseDto;
    }

    @Override
    public List<UserResponseDto> getUserData() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();

        for (User user : users) {
            UserResponseDto response = new UserResponseDto();
            response.setId(user.getUserId());
            String token = jwtConfig.generateToken(user);
            response.setToken(token);
            response.setEmail(user.getEmail());
            response.setSuccess(true);
            response.setMessage("User successfully retrieved.");
            userResponseDtos.add(response);
        }
        return userResponseDtos;
    }

    @Override
    public UserResponseDto updateUser(String userId, UserUpdateDto userUpdateDto) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }



}
