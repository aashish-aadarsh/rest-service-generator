package com.devop.aashish.java.myapplication.domain.user.repository;

import com.devop.aashish.java.myapplication.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndUserSecurity_Otp(String email, Integer otp);
}
