package com.cdcm.backend.security.utils;

import com.cdcm.backend.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    public static final int EXPIRATION_TIME_IN_MINUTES = 1440;
    private  String SECRET_KEY = "73ff0453aabc59894874205612f3d78594e383c147895e2f41a106da9183a213";

    public String generateJwt(UserEntity user) {
        return generateJwt(new HashMap<>(), user);
    }

    public String generateJwt(Map<String, Object> extraClaims, UserEntity user) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + EXPIRATION_TIME_IN_MINUTES * 1000 * 60);
        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String jwtToken) {
        return getClaim(jwtToken, Claims::getSubject);
    }

    private <T> T getClaim(String jwtToken, Function<Claims, T> claimsTFunction) {
        final Claims claims = getAllClaims(jwtToken);
        return claimsTFunction.apply(claims);
    }

    private Claims getAllClaims(String jwtToken) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }
}
