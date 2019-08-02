package com.devop.aashish.java.myapplication.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@Document(collection = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    public String _id;

    public Boolean isActive = Boolean.TRUE;

    @NotBlank(message = "name can not be blank")
    private String name;

    @Email(message = "Not a valid email Id")
    @NotBlank(message = "email can not be blank")
    private String email;

    @NotBlank(message = "mobileNumber can not be null")
    private String mobileNumber;

    @JsonIgnore
    private UserSecurity userSecurity;

    @JsonIgnore
    private Set<UserRole> userRoles;


}

