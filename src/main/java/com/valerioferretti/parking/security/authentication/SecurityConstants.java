package com.valerioferretti.parking.security.authentication;

public class SecurityConstants {
    public static final long JWT_DURATION = 86400000; // 1 day
    public static final String JWT_HEADER_KEY = "Authorization";
    public static final String ADMIN_SIGNUP_URL = "/api/v1/account/admin/signup";
    public static final String USER_SIGNUP_URL = "/api/v1/account/user/signup";
    public static final String LOGIN_URL = "/api/v1/account/login";
}
