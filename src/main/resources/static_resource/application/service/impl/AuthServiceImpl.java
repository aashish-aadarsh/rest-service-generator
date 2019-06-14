package com.devop.aashish.java.myapplication.application.service.impl;

import com.devop.aashish.java.myapplication.application.constant.SuccessMessage;
import com.devop.aashish.java.myapplication.application.constant.SystemRole;
import com.devop.aashish.java.myapplication.application.exception.InvalidInputException;
import com.devop.aashish.java.myapplication.application.exception.UnAuthenticatedException;
import com.devop.aashish.java.myapplication.application.exception.UnAuthorizedException;
import com.devop.aashish.java.myapplication.application.service.AuthService;
import com.devop.aashish.java.myapplication.application.utility.OTPGenerator;
import com.devop.aashish.java.myapplication.application.utility.SecurityUtil;
import com.devop.aashish.java.myapplication.configuration.aop.annotation.LogMethodInfo;
import com.devop.aashish.java.myapplication.configuration.security.jwt.JwtTokenProvider;
import com.devop.aashish.java.myapplication.configuration.security.vo.JwtAuthenticationResponse;
import com.devop.aashish.java.myapplication.configuration.security.vo.LoginRequest;
import com.devop.aashish.java.myapplication.domain.user.User;
import com.devop.aashish.java.myapplication.domain.user.UserRole;
import com.devop.aashish.java.myapplication.domain.user.UserSecurity;
import com.devop.aashish.java.myapplication.domain.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@LogMethodInfo
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPGenerator otpGenerator;


    @Override
    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateUserToken(authentication);
        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    public User registerUser(User user, String password) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new InvalidInputException("Email Id is already used.");
        } else {
            user.setUserRoles(Collections.singleton(UserRole.builder()
                    .roleCode(SystemRole.USER_ROLE)
                    .roleName(SystemRole.USER_ROLE_NAME)
                    .build()));
            UserSecurity userSecurity = UserSecurity.builder()
                    .password(passwordEncoder.encode(password))
                    .otp(otpGenerator.generateOTP())
                    .accountBlocked(Boolean.FALSE)
                    .accountVerified(Boolean.FALSE)
                    .build();

            user.setUserSecurity(userSecurity);
            user.createdDateTime = new Date();
            user.updatedDateTime = null;
            user._id = null;
            return userRepository.save(user);
        }
    }

    @Override
    public String activateUser(String email, Integer otp) {
        Optional<User> userOp = userRepository.findByEmailAndUserSecurity_Otp(email, otp);
        if (!userOp.isPresent()) {
            throw new InvalidInputException("Email Id and OTP not matched.");
        }
        User user = userOp.get();
        UserSecurity userSecurity = user.getUserSecurity();
        userSecurity.setAccountVerified(Boolean.TRUE);
        userSecurity.setOtp(null);
        user.updatedDateTime = new Date();
        userRepository.save(user);
        return SuccessMessage.USER_ACTIVATION;
    }

    @Override
    public String forgotPassword(String email) {
        Optional<User> userOp = userRepository.findByEmail(email);
        if (!userOp.isPresent()) {
            throw new InvalidInputException("Email Id not valid.");
        }
        User user = userOp.get();
        UserSecurity userSecurity = user.getUserSecurity();
        userSecurity.setOtp(otpGenerator.generateOTP());
        user.updatedDateTime = new Date();
        userRepository.save(user);
        return SuccessMessage.USER_FORGOT_PASSWORD_OTP_GENERATED;
    }

    @Override
    public String changePasswordWithOTP(String email, Integer otp, String newPassword) {
        Optional<User> userOp = userRepository.findByEmailAndUserSecurity_Otp(email, otp);
        if (!userOp.isPresent()) {
            throw new UnAuthenticatedException("Email Id or OTP not valid.");
        }
        User user = userOp.get();
        UserSecurity userSecurity = user.getUserSecurity();
        userSecurity.setOtp(null);
        userSecurity.setPassword(passwordEncoder.encode(newPassword));
        user.updatedDateTime = new Date();
        userRepository.save(user);
        return SuccessMessage.USER_FORGOT_PASSWORD_CHANGED;
    }

    @Override
    public String changePassword(String email, String oldPassword, String newPassword) {
        if (!SecurityUtil.loggedInUser().getEmail().equals(email)) {
            throw new UnAuthorizedException("Not authorized to change password of other user");
        }
        Optional<User> userOp = userRepository.findByEmail(email);
        if (!userOp.isPresent()) {
            throw new UnAuthenticatedException("Email Id is not valid.");
        }
        User user = userOp.get();
        UserSecurity userSecurity = user.getUserSecurity();
        if (!passwordEncoder.matches(oldPassword, userSecurity.getPassword())) {
            throw new UnAuthenticatedException("Old Password is not valid.");
        }
        userSecurity.setPassword(passwordEncoder.encode(newPassword));
        user.updatedDateTime = new Date();
        userRepository.save(user);
        return SuccessMessage.USER_PASSWORD_CHANGED;
    }
}
