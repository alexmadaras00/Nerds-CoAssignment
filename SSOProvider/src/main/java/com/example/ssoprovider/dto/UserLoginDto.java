package com.example.ssoprovider.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    private String email;
    private String password;

    public UserLoginDto(){}
    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
