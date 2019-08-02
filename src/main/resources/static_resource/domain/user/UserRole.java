package com.devop.aashish.java.myapplication.domain.user;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    private String roleCode;
    private String roleName;
}
