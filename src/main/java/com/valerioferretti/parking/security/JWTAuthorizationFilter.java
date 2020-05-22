package com.valerioferretti.parking.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.valerioferretti.parking.model.UserProfile;
import com.valerioferretti.parking.service.UserProfileService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
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
        Set authorities;
        String email;
        String[] claims;
        Claim claim;

        if(token == null) {
            return null;
        }

        authorities = new HashSet<>();
        email = JWT.require(Algorithm.HMAC512(SecurityConstants.JWT_SECRET.getBytes()))
                .build().verify(token).getSubject();
        claim = JWT.require(Algorithm.HMAC512(SecurityConstants.JWT_SECRET.getBytes()))
                    .build().verify(token).getClaim(SecurityConstants.AUTHORITIES_KEY);
        claims =  claim.asString().split(",");
        for (String role : claims){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return new UsernamePasswordAuthenticationToken(email, null, authorities);
    }

}
