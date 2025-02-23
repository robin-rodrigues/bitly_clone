package com.bitly.clone.security.jwt;

import com.bitly.clone.service.UserDetailsImpl;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    /**
     * Extracts the JWT token from the Authorization header of the request.
     * - The expected request format: Authorization -> Bearer <TOKEN>.
     * - If the header exists and starts with "Bearer ", the method extracts and returns the token.
     * - Returns null if no valid Bearer token is found.
     *
     * @param request the HTTP request containing the Authorization header
     * @return the extracted JWT token or null if not present
     */
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    /**
     * Generates a JWT token for the authenticated user.
     * - Retrieves the username and roles from the provided UserDetails.
     * - Uses JWT claims to include roles, issued timestamp, and expiration time.
     * - Signs the token with a secure key.
     *
     * @param userDetails the authenticated user's details
     * @return a signed JWT token as a String
     */
    public String generateToken(UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
        String roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    /**
     * Extracts the username from a given JWT token.
     * - Parses the token to retrieve the subject (username).
     * - Uses a secure key to verify the token's integrity.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    /**
     * Generates and returns the signing key used for JWT token creation and validation.
     * - Decodes the base64-encoded secret key.
     * - Uses HMAC-SHA for cryptographic signing.
     *
     * @return the cryptographic key used for signing JWTs
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Validates a given JWT token.
     * - Parses the token and verifies its integrity using the signing key.
     * - Returns true if the token is valid, otherwise throws an exception.
     * - Catches common JWT exceptions and rethrows them as runtime exceptions.
     *
     * @param authToken the JWT token to validate
     * @return true if the token is valid; otherwise, throws an exception
     * @throws RuntimeException if the token is invalid or an error occurs
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().verifyWith((SecretKey) key())
                    .build().parseSignedClaims(authToken);
            return true;
        } catch (JwtException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
