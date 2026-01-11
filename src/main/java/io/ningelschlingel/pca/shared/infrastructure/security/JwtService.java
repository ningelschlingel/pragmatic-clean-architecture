package io.ningelschlingel.pca.shared.infrastructure.security;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.ningelschlingel.pca.shared.core.domain.AuthenticatedUser;
import io.ningelschlingel.pca.shared.core.domain.UserId;

@Component
public class JwtService {
    private final SecretKey key = Jwts.SIG.HS256.key().build();
    private final long expiration = 3600000; // 1 Stunde

    public String createToken(UserId principalId, String email) {
        return Jwts.builder()
                .subject(principalId.value().toString())
                .claim("email", email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public Optional<AuthenticatedUser> verifyToken(String token) {
        try {
            Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            return Optional.of(new AuthenticatedUser(
                    UserId.of(UUID.fromString(claims.getSubject())),
                    claims.get("email", String.class)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}