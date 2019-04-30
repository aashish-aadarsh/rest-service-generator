package com.devop.aashish.java.myapplication.controller;

import com.devop.aashish.java.myapplication.application.service.AuthService;
import com.devop.aashish.java.myapplication.configuration.aop.annotation.LogExecutionTime;
import com.devop.aashish.java.myapplication.configuration.aop.annotation.LogMethodInfo;
import com.devop.aashish.java.myapplication.configuration.security.vo.LoginRequest;
import com.devop.aashish.java.myapplication.domain.user.User;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth")
@LogExecutionTime
@LogMethodInfo
@Api(tags = {"Authorization"})
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/login/v1")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    @PostMapping("/register/v1")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, @RequestHeader(name = "X-PASS") String password) {
        return ResponseEntity.ok(authService.registerUser(user, password));
    }

    @GetMapping("/activate/v1")
    public ResponseEntity<?> activateUser(@RequestParam(name = "email") String email, @RequestParam(name = "otp") Integer otp) {
        return ResponseEntity.ok(authService.activateUser(email, otp));
    }

    @GetMapping("/forgot/v1")
    public ResponseEntity<?> forgotPassword(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(authService.forgotPassword(email));
    }

    @PutMapping("/forgot/v1")
    public ResponseEntity<?> changePasswordWithOTP(@RequestParam(name = "email") String email,
                                                   @RequestParam(name = "otp") Integer otp,
                                                   @RequestHeader(name = "X-PASS-NEW") String newPassword) {
        return ResponseEntity.ok(authService.changePasswordWithOTP(email, otp, newPassword));
    }

    @PutMapping("/change/v1")
    public ResponseEntity<?> changePassword(@RequestParam(name = "email") String email,
                                            @RequestHeader(name = "X-PASS") String oldPassword,
                                            @RequestHeader(name = "X-PASS-NEW") String newPassword) {
        return ResponseEntity.ok(authService.changePassword(email, oldPassword, newPassword));
    }

}
