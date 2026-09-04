package Myproject.FINTRACK.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final SecretKey secretKey;
    private final long expiration;

    public JwtService(@Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration){
            this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
            this.expiration = expiration;
    }
    public String generateToken(String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return io.jsonwebtoken.Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }
    public String extractUserName(String token) {
            return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();  
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
                System.out.println("Token is valid");
            return true;
        } catch (Exception e) {
            System.out.println("Token is invalid: " + e.getMessage());
            return false;
        }
    }
}
