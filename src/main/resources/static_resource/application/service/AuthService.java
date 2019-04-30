package com.devop.aashish.java.myapplication.application.service;

import com.devop.aashish.java.myapplication.configuration.security.vo.JwtAuthenticationResponse;
import com.devop.aashish.java.myapplication.configuration.security.vo.LoginRequest;
import com.devop.aashish.java.myapplication.domain.user.User;

public interface AuthService {

    JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);

    User registerUser(User user, String password);

    String activateUser(String email, Integer otp);

    String forgotPassword(String email);

    String changePasswordWithOTP(String email, Integer otp, String newPassword);

    String changePassword(String email, String oldPassword, String newPassword);
}
