package com.son.todolist.common.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.son.todolist.common.helper.Constant.ONE_WEEK;

@Component
public class JwtHelper {
    @Value("${jwt.secret-key}")
    private String secretKey;

    public String createToken(Authentication authentication) {
        SecretKey key = getSecretKey();
        long expMillis = System.currentTimeMillis() + ONE_WEEK;
        Date expiration = new Date(expMillis);

        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(expiration)
                .subject(authentication.getName())
                .signWith(key)
                .compact();
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null)
            return null;

        return token.substring(7);
    }

    public boolean validateToken(String token) {
        try {
            SecretKey key = getSecretKey();
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        try {
            SecretKey key = getSecretKey();
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            Claims claims = claimsJws.getPayload();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
