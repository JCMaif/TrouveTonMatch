package org.simplon.TrouveTonMatch.model;

public enum UserRole {
    ADMIN("admin"),
    PORTEUR("porteur"),
    PLATEFORME("plateforme"),
    PARRAIN("parrain"),
    USER("user");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
