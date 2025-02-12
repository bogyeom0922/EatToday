package com.eattoday.Eattoday.security.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JWTUtil {

    private Key key;
    public JWTUtil(@Value("${jwt.secret}") String secret) {
        byte[] secretKey = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(secretKey);
    }



}
