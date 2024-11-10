package br.com.glojura.demo_spring_security.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class TokenGenerator {

    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);


    public static String generateToken(String subject, String scopes) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("scope", scopes)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public static SecretKey getSecretKey() {
        return secretKey;
    }
}