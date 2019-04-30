package com.devop.aashish.java.myapplication.domain.user;

import com.devop.aashish.java.myapplication.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@Document(collection = "user")
public class User extends BaseEntity {

    @NotBlank(message = "firstName can not be blank")
    private String firstName;

    private String lastName;
    @Email(message = "Not a valid email Id")
    @NotBlank(message = "email can not be blank")
    private String email;

    @NotBlank(message = "mobileNumber can not be null")
    private String mobileNumber;

    @JsonIgnore
    private UserSecurity userSecurity;

    private Set<UserRole> userRoles;
    

}

