package com.valerioferretti.parking.model.enums;

public enum RoleType {
    USER(RoleTypeValues.USER),
    ADMIN(RoleTypeValues.ADMIN);

    private String role;

    RoleType(String role) {
        this.role = role;
    }

    public String value() {
        return role;
    }

    public class RoleTypeValues {
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
    }
}
