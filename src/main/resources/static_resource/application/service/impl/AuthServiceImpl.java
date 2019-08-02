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
import com.devop.aashish.java.myapplication.configuration.security.OTPAuthenticationToken;
import com.devop.aashish.java.myapplication.configuration.security.vo.UserPrincipal;
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
        if (userRepository.findByEmailOrMobileNumber(user.getEmail(), user.getMobileNumber()).isPresent()) {
            throw new InvalidInputException("Email Id Or Mobile Number is already used.");
        } else {
            user.setUserRoles(Collections.singleton(UserRole.builder()
                    .roleCode(SystemRole.USER_ROLE)
                    .roleName(SystemRole.USER_ROLE_NAME)
                    .build()));
            Integer otp = otpGenerator.generateOTP();
            UserSecurity userSecurity = UserSecurity.builder()
                    .password(passwordEncoder.encode(password))
                    .otp(otp)
                    .accountBlocked(Boolean.FALSE)
                    .accountVerified(Boolean.FALSE)
                    .build();

            user.setUserSecurity(userSecurity);
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
        userRepository.save(user);
        return SuccessMessage.USER_PASSWORD_CHANGED;
    }

    @Override
    public String authenticateUserMobile(String mobileNumber) {
        Optional<User> userOp = userRepository.findByMobileNumber(mobileNumber);
        if (!userOp.isPresent()) {
            throw new InvalidInputException("Mobile Number is Not registered");
        }
        User user = userOp.get();
        UserSecurity userSecurity = user.getUserSecurity();
        Integer otp = otpGenerator.generateOTP();
        userSecurity.setOtp(otp);
        userRepository.save(user);
        return SuccessMessage.USER_OTP_LOGIN;
    }


    @Override
    public JwtAuthenticationResponse authenticateUserMobileOTP(String mobileNumber, Integer otp) {
        Optional<User> userOp = userRepository.findByMobileNumberAndUserSecurity_Otp(mobileNumber, otp);
        if (!userOp.isPresent()) {
            throw new UnAuthenticatedException("Mobile Number or OTP not valid.");
        }
        User user = userOp.get();
        UserSecurity userSecurity = user.getUserSecurity();
        Authentication authentication = authenticationManager.authenticate(
                new OTPAuthenticationToken(UserPrincipal.create(user, null))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateUserToken(authentication);
        userSecurity.setOtp(null);
        userRepository.save(user);
        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    public String resendOTP(String mobileNumber) {
        Optional<User> userOp = userRepository.findByMobileNumberAndUserSecurity_OtpIsNotNull(mobileNumber);
        if (!userOp.isPresent()) {
            throw new UnAuthenticatedException("Mobile Number not valid or OTP is not generated yet.");
        }
        User user = userOp.get();
        UserSecurity userSecurity = user.getUserSecurity();
        Integer otp = otpGenerator.generateOTP();
        userSecurity.setOtp(otp);
        userRepository.save(user);
        return SuccessMessage.USER_OTP_RESENT;
    }
}
