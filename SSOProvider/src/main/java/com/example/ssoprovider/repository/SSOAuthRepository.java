package com.example.ssoprovider.repository;

import com.example.ssoprovider.dto.UserLoginDto;
import com.example.ssoprovider.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SSOAuthRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);
}
