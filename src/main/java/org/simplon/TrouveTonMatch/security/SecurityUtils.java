package org.simplon.TrouveTonMatch.security;

import org.mapstruct.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private final JwtService jwtService;

    @Autowired
    public SecurityUtils(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public Long getAuthenticatedUserPlateformeId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            System.out.println("❌ Authentication is null");
            return null;
        }

        System.out.println("✅ Authentication: " + authentication);

        if (authentication.getCredentials() == null) {
            System.out.println("❌ Credentials (token) is null");
            return null;
        }

        String token = authentication.getCredentials().toString();
        System.out.println("✅ Extracted Token: " + token);

        Long plateformeId = jwtService.extractPlateformeId(token);
        System.out.println("✅ Extracted PlateformeId: " + plateformeId);

        return plateformeId;
    }
}
