package com.devop.aashish.java.myapplication.application.utility;

import com.devop.aashish.java.myapplication.application.exception.UnAuthenticatedException;
import com.devop.aashish.java.myapplication.configuration.security.vo.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtil {

    public static UserPrincipal loggedInUser() {
        if (SecurityContextHolder.getContext() == null ||
                SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new UnAuthenticatedException("User is not logged in");
        }
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
            throw new UnAuthenticatedException("User is not logged in");
        }
        return (UserPrincipal) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
    }
}
