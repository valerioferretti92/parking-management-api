package com.valerioferretti.parking.security.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valerioferretti.parking.config.JwtConfig;
import com.valerioferretti.parking.model.UserProfile;
import com.valerioferretti.parking.service.UserProfileService;
import com.valerioferretti.parking.utils.Utils;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

public class JwtAuthenticationManager extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private UserProfileService userProfileService;
    private JwtConfig jwtConfig;

    public JwtAuthenticationManager(AuthenticationManager authenticationManager,
                                    UserProfileService userProfileService,
                                    JwtConfig jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.userProfileService = userProfileService;
        this.jwtConfig = jwtConfig;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        UserProfile userProfile;
        ApplicationUser credentials;
        Collection<? extends GrantedAuthority> authorities;
        UsernamePasswordAuthenticationToken authentication;

        credentials = new ObjectMapper().readValue(req.getInputStream(), ApplicationUser.class);
        userProfile = userProfileService.get(credentials.getEmail());
        authorities = Utils.getAuthoritiesFromRoles(userProfile.getRoles());
        authentication = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword(), authorities);
        return authenticationManager.authenticate(authentication);
    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                            FilterChain chain, Authentication auth) {
        String token;

        token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.JWT_DURATION))
                .sign(Algorithm.HMAC512(jwtConfig.getJwtSecretRawBytes()));
        res.addHeader(SecurityConstants.JWT_HEADER_KEY, token);
    }

    @Data
    public static class ApplicationUser {
        private String email;
        private String password;
    }
}