package org.simplon.TrouveTonMatch.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.simplon.TrouveTonMatch.model.Utilisateur;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class JwtService {

    @Value("${spring.security.jwt.secret}")
    private String secret;

    @Value("${spring.security.jwt.expiration}")
    private long expiration;


    public String generateToken(Utilisateur user) {
        String role = user.getRole().name();
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var token = JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("role", role)
                    .withClaim("username", user.getUsername())
                    .withClaim("id", user.getId())
                    .withClaim("plateformeId", user.getPlateforme() != null ? user.getPlateforme().getId() : null)
                    .withClaim("enabled", user.getEnabled())
                    .withExpiresAt(genAccessTokenExpirationDate())
                    .sign(algorithm);

            System.out.println("Token généré : " + JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("role", role)
                    .withClaim("username", user.getUsername())
                    .withClaim("id", user.getId())
                    .withClaim("enabled", user.getEnabled())
                    .withClaim("plateformeId", user.getPlateforme() != null ? user.getPlateforme().getId() : null)
                    .sign(algorithm));
            return token;
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Error while creating JWT", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Error while validating JWT", e);
        }
    }

    public String extractRole(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(token);
            return decodedJWT.getClaim("role").asString();
        } catch (Exception e) {
            throw new RuntimeException("Error while extracting role from JWT", e);
        }
    }

    public Long extractPlateformeId(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(token);
            return decodedJWT.getClaim("plateformeId").asLong();
        } catch (Exception e) {
            throw new RuntimeException("Error while extracting plateformeId from JWT", e);
        }
    }
    public Long extractUserId(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(token);
            return decodedJWT.getClaim("id").asLong();
        } catch (Exception e) {
            throw new RuntimeException("Error while extracting userId from JWT", e);
        }
    }

    private Instant genAccessTokenExpirationDate() {
        return LocalDateTime.now()
                .plusDays(expiration)
                .atZone(ZoneId.systemDefault())
                .toInstant();
    }
}
