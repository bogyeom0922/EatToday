package com.eattoday.Eattoday.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    private Key key;

    public JWTUtil(@Value("${jwt.secret}") String secret) {
        byte[] secretKey = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(secretKey);
    }

    public String getUserName(String token){
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("username", String.class);
        }
        catch(ExpiredJwtException e){
            System.out.println("JWT token is expired: " + e.getMessage());
            return null;
        }
    }

    public String getRole(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role", String.class);
    }


    public Boolean isExpired(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            System.out.println("Token parsing error: " + e.getMessage());
            return true;
        }
    }

    public String createJwt(String username, String role, Long expiredMs) {

        Claims claims = Jwts.claims();
        claims.put("username", username);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}
