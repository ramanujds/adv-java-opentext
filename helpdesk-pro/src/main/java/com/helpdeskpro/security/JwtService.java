package com.helpdeskpro.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private final SecretKey signingKey;
    private final long expirationMillis;

    public JwtService(@Value("${security.jwt.secret}") String base64Secret,
                      @Value("${security.jwt.expiration-minutes}") long expirationMinutes) {
        this.signingKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Secret));
        this.expirationMillis = expirationMinutes * 60 * 1000;
    }

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMillis);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey)
                .compact();
    }

    /** @return the subject (employee email) if the token is well-formed and unexpired, else empty. */
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    private Claims parseClaims(String token) {
        // Throws JwtException (bad signature, malformed, expired via a dedicated subtype,
        // etc.) — the filter decides what to do with that; this class only knows JWT mechanics.
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public long getExpirationSeconds() {
        return expirationMillis / 1000;
    }

    public boolean isWellFormedAndUnexpired(String token) {
        try {
            return !isExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}


