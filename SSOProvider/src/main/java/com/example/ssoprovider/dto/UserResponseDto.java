package com.example.ssoprovider.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private boolean success;
    private String message;
    private String token;
    private String id;
    private String email;

    public UserResponseDto() {}
    public UserResponseDto(boolean success, String mail, String token) {
        this.success = success;
        this.email = mail;
        this.token = token;
    }
}
