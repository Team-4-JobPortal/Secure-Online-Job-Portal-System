package com.example.security;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/* 
 * Utility class for JWT token operations including generation, validation, and parsing.
 * Uses HS256 algorithm with securely generated key for token signing.
 */
public class JwtUtil {
    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION = 3600000; // 1 hour in milliseconds

    /* 
     * Generates a JWT token for the given username with expiration.
     * Token includes subject (username) and expiration time, signed with secret key.
     * @param username The username/email to embed in the token subject
     * @return String containing the signed JWT token
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(KEY)
                .compact();
    }

    /* 
     * Extracts username from JWT token by parsing the subject claim.
     * Verifies token signature before extracting data.
     * @param token The JWT token string to parse
     * @return String containing the username from token subject, or null if invalid
     */
    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /* 
     * Validates JWT token signature and expiration time.
     * Checks if token is properly signed and not expired.
     * @param token The JWT token string to validate
     * @return boolean true if token is valid, false if invalid or expired
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
