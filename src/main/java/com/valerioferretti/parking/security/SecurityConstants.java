package com.valerioferretti.parking.security;

public class SecurityConstants {
    public static final String JWT_SECRET = "SecretKeyToGenJWTs";
    public static final long JWT_DURATION = 864_000_000; // 10 days
    public static final String JWT_HEADER_KEY = "Authorization";
    public static final String AUTHORITIES_KEY = "Authorities";
    public static final String ADMIN_SIGNUP_URL = "/api/v1/account/admin/signup";
    public static final String USER_SIGNUP_URL = "/api/v1/account/user/signup";
    public static final String LOGIN_URL = "/api/v1/account/login";
}
