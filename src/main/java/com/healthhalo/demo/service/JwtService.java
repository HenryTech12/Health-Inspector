package com.healthhalo.demo.service;

import com.healthhalo.demo.dto.UserData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    private String key = null;
    public SecretKey getSecretKey() {
        SecretKey secretKey = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            secretKey = keyGenerator.generateKey();
            key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        }
        catch(NoSuchAlgorithmException noSuchAlgorithmException) {
            log.error("error: {}",noSuchAlgorithmException.getMessage());
        }
        return secretKey;
    }

    public Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String getToken(UserData userData) {
        Map<String,String> claims = new HashMap<>();
        return Jwts.builder()
                .claims(claims)
                .subject(userData.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() * 1000 + 30))
                .signWith(getKey())
                .compact();
    }

    public Claims extractAllClaim(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build().parseSignedClaims(token).getPayload();
    }

    public <T>T extractClaims(String token, Function<Claims,T> claimsResolver) {
        Claims claims = extractAllClaim(token);
        return  claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaims(token,Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return (
                extractClaims(token,Claims::getExpiration).
                        before(new Date(System.currentTimeMillis()))
                &&
                        extractUsername(token).equals(userDetails.getUsername())
        );
    }
}
