package com.devop.aashish.java.myapplication.configuration.security.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest {
    @NotBlank
    private String login;

    @NotBlank
    private String password;

}
