package com.example.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private long expiration;
    
    // ✅ Correct generateToken method (no change needed)
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    // ✅ Corrected getUsernameFromToken method
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                   .verifyWith(getSigningKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload()
                   .getSubject();
    }
    
    // ✅ Corrected validateToken method
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    // ✅ Corrected isTokenExpired method
    public boolean isTokenExpired(String token) {
        try {
            Date expirationDate = Jwts.parser()
                                      .verifyWith(getSigningKey())
                                      .build()
                                      .parseSignedClaims(token)
                                      .getPayload()
                                      .getExpiration();
            return expirationDate.before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return true; // Token is invalid or expired
        }
    }
    
    // ✅ Corrected getSigningKey method
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
