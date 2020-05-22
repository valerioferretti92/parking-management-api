package com.valerioferretti.parking.model;

import com.valerioferretti.parking.model.enums.RoleTypes;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserProfile {

    public UserProfile() {
        roles = new HashSet<>();
    }

    @Id
    @NotNull
    private String email;
    @Field
    @NotNull
    private String password;
    @Field
    private Set<RoleTypes> roles;
}
