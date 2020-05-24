package com.valerioferretti.parking.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.valerioferretti.parking.model.UserProfile;
import com.valerioferretti.parking.service.UserProfileService;
import com.valerioferretti.parking.utils.Utils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserProfileService userProfileService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  UserProfileService userProfileService) {
        super(authenticationManager);
        this.userProfileService = userProfileService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String token;

        token = req.getHeader(SecurityConstants.JWT_HEADER_KEY);
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(token));
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Set<GrantedAuthority> authorities;
        UserProfile userProfile;
        String email, encodedPassword;

        if(token == null) {
            return null;
        }

        email = JWT.require(Algorithm.HMAC512(SecurityConstants.JWT_SECRET.getBytes()))
                .build().verify(token).getSubject();
        userProfile = userProfileService.get(email);
        encodedPassword = userProfile.getPassword();
        authorities = Utils.getAuthoritiesFromRoles(userProfile.getRoles());
        return new UsernamePasswordAuthenticationToken(email, encodedPassword, authorities);
    }

}
