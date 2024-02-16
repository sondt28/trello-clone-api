package com.son.todolist.common.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;

import java.time.Instant;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

import static com.son.todolist.common.helper.Constant.OTP_LENGTH;

public class TokenHelper {

    public static String generateOtp() {
        SplittableRandom splittableRandom = new SplittableRandom();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            stringBuilder.append(splittableRandom.nextInt(0, 10));
        }

        return stringBuilder.toString();
    }

    public static JwtClaimsSet generateJwtToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        return JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plusSeconds(100000))
                .issuer("http://localhost:8080")
                .subject(authentication.getName())
                .claim("scp", scope)
                .build();
    }
}
