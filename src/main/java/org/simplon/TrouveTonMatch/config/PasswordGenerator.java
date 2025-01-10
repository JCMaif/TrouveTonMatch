package org.simplon.TrouveTonMatch.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String adminPassword = passwordEncoder.encode("admin");
        String userPassword = passwordEncoder.encode("user");
        String porteurPassword = passwordEncoder.encode("porteur");
        String parrainPassword = passwordEncoder.encode("parrain");

        System.out.println("admin password : " + adminPassword);
        System.out.println("user password : " + userPassword);
        System.out.println("porteur password : " + porteurPassword);
        System.out.println("parrain password : " + parrainPassword);
    }
}
