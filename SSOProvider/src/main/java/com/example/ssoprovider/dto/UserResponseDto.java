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

}
