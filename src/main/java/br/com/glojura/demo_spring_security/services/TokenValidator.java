package br.com.glojura.demo_spring_security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;

public class TokenValidator {

    public static Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(TokenGenerator.getSecretKey()) // A mesma chave secreta utilizada para gerar o token
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Token is invalid: " + e.getMessage());
        }
    }

}
