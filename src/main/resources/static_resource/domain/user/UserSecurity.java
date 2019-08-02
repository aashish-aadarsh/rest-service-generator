package com.devop.aashish.java.myapplication.domain.user;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSecurity {
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private Integer otp;
    private Boolean accountVerified;
    private Boolean accountBlocked;
    private String otpExpiryDateTime;
}
