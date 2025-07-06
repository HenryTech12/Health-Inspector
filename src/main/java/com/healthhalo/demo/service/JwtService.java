package com.healthhalo.demo.service;

import com.healthhalo.demo.dto.UserData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    private String secretKey = "";
    public JwtService() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey myKey = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(myKey.getEncoded());
        }
        catch(NoSuchAlgorithmException noSuchAlgorithmException) {
            log.error("error: {}",noSuchAlgorithmException.getMessage());
        }
    }

    public String generateToken(UserData userData) {
        Map<String,String> claims = new HashMap<>();
        return Jwts.builder()
                .claims(claims)
                .subject(userData.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() * 1000 + 10))
                .signWith(getKey())
                .compact();
    }

    public SecretKey getKey() {
        byte[] myKeybytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(myKeybytes);
    }


    public String extractEmail(String token) {
        return extractClaims(token,Claims::getSubject);
    }

    public <T>T extractClaims(String token, Function<Claims,T> claimsResolver) {
        Claims claims = extractClaim(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractClaim(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenExpired(String token) {
        return new Date(System.currentTimeMillis()).after(
                extractClaims(token,Claims::getExpiration));
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        System.out.println(userDetails.getUsername());
        return (!isTokenExpired(token) && (extractEmail(token).equals(userDetails.getUsername())));
    }
}
