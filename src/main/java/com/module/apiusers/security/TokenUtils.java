package com.module.apiusers.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {
    private final static String ACCESS_TOKEN_SECRET = "asdas453dadasdas3d4asd4sa45asd5";
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L;

    public static String createToken(String name, String email){
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS *1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("name", name);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(SignatureAlgorithm.HS512, ACCESS_TOKEN_SECRET.getBytes())
                .compact();
    }
}
