package com.valerioferretti.parking.model.enums;

public enum RoleTypes {
    USER("USER"),
    ADMIN("ADMIN");

    private String role;

    RoleTypes(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
