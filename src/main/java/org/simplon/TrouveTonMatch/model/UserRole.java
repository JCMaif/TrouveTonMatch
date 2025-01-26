package org.simplon.TrouveTonMatch.model;

public enum UserRole {
    ADMIN("admin"),
    PORTEUR("porteur"),
    STAFF("staff"),
    PARRAIN("parrain"),
    USER("user");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
