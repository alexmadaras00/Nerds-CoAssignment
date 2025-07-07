package com.example.ssoprovider.repository;

import com.example.ssoprovider.dto.UserLoginDto;
import com.example.ssoprovider.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SSOAuthRepository extends JpaRepository<User,String> {
}
