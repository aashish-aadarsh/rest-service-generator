package com.devop.aashish.java.myapplication.configuration.security;

import com.devop.aashish.java.myapplication.application.exception.UnAuthenticatedException;
import com.devop.aashish.java.myapplication.configuration.security.vo.UserPrincipal;
import com.devop.aashish.java.myapplication.domain.user.User;
import com.devop.aashish.java.myapplication.domain.user.UserSecurity;
import com.devop.aashish.java.myapplication.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApplicationUserAuthService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = repository.findByEmail(login);
        if (!user.isPresent()) {
            throw new UnAuthenticatedException("User with email not found");
        }

        if (user.get().isActive.equals(Boolean.FALSE)) {
            throw new UnAuthenticatedException("User with email is not Active");
        }
        UserSecurity userSecurity = user.get().getUserSecurity();
        if (!userSecurity.getAccountVerified()) {
            throw new UnAuthenticatedException("User with email is Not Verified");
        } else if (userSecurity.getAccountBlocked()) {
            throw new UnAuthenticatedException("User with email is Blocked");
        }
        return UserPrincipal.create(user.get(), userSecurity.getPassword());
    }

    public UserDetails loadUserById(String id) {
        User user = repository.findById(id).orElseThrow(
                () -> new UnAuthenticatedException("User with Id not found"));
        return UserPrincipal.create(user, user.getUserSecurity().getPassword());
    }

}
