package org.simplon.TrouveTonMatch.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.simplon.TrouveTonMatch.model.UserApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class JwtService {

    @Value("${spring.security.jwt.secret}")
    private String secret;

    @Value("${spring.security.jwt.expiration}")
    private long expiration;


    public String generateToken(UserApi user) {
        String role = user.getRole().name();
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var token = JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("role", role)
                    .withClaim("username", user.getUsername())
                    .withClaim("id", user.getId())
                    .withExpiresAt(genAccessTokenExpirationDate())
                    .sign(algorithm);

            System.out.println("Token généré : " + JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("role", role)
                    .withClaim("username", user.getUsername())
                    .withClaim("id", user.getId())
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

    private Instant genAccessTokenExpirationDate() {
        return LocalDateTime.now()
                .plusDays(expiration)
                .atZone(ZoneId.systemDefault())
                .toInstant();
    }
}
