package com.devop.aashish.java.myapplication.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserRole {

    private String roleCode;
    private String roleName;
}
