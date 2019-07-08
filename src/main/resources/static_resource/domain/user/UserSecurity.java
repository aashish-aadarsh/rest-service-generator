package com.devop.aashish.java.myapplication.domain.user;

import com.devop.aashish.java.myapplication.domain.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserSecurity extends BaseEntity {
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private Integer otp;
    private Boolean accountVerified;
    private Boolean accountBlocked;
    private String otpExpiryDateTime;
}
