package com.devop.aashish.java.myapplication.configuration.security.vo;

import com.devop.aashish.java.myapplication.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private String id;

    private String firstName;

    private String lastName;

    private String email;


    private Collection<? extends GrantedAuthority> authorities;

    @JsonIgnore
    private String password;


    private UserPrincipal(String id, String firstName, String lastName, String email, Collection<? extends GrantedAuthority> authorities, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.authorities = authorities;
        this.password = password;
    }

    public static UserPrincipal create(User user, String password) {

        Set<String> auth = new HashSet<>();
        user.getUserRoles().forEach(role -> {
            auth.add(role.getRoleCode());
        });

        List<GrantedAuthority> authorities = auth.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new UserPrincipal(
                user._id,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                authorities,
                password
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return id;
    }

}
