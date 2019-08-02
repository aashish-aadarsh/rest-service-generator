package com.devop.aashish.java.myapplication.configuration.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class OTPAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return new OTPAuthenticationToken(authentication.getPrincipal());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                OTPAuthenticationToken.class);
    }
}
