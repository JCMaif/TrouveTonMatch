package org.simplon.TrouveTonMatch.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.simplon.TrouveTonMatch.model.UserApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
public class JwtService {

    @Value("${spring.security.jwt.secret}")
    private String secret;

    @Value("${spring.security.jwt.expiration}")
    private long expiration;


    public String generateToken(UserApi user) {
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("roles", roles)
                    .withClaim("username", user.getUsername())
                    .withExpiresAt(genAccessTokenExpirationDate())
                    .sign(algorithm);
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

    public List<String> extractRoles(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(token);
            return decodedJWT.getClaim("roles").asList(String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error while extracting roles from JWT", e);
        }
    }

    private Instant genAccessTokenExpirationDate() {
        return LocalDateTime.now().plusDays(expiration).atZone(ZoneId.systemDefault()).toInstant();
    }
}
