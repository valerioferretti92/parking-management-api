package com.valerioferretti.parking.security.authorization;

import com.valerioferretti.parking.model.enums.RoleType;
import com.valerioferretti.parking.utils.Utils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthorizationManager {

    public boolean hasRole(String roleType) {
        Authentication authentication;
        Set<RoleType> roles;

        authentication = SecurityContextHolder.getContext().getAuthentication();
        roles = Utils.getRolesFromAuthorities(authentication.getAuthorities());
        return roles.contains(RoleType.valueOf(roleType));
    }
}
