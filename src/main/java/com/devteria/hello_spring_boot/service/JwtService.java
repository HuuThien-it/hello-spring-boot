
package com.devteria.hello_spring_boot.service;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Base64;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final long expiration;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration) {

        // Chuyển khóa Base64 thành mảng byte
        byte[] keyBytes = Base64.getDecoder().decode(secret);

        // Tạo khóa bí mật dùng thuật toán HS256
        SecretKey secretKey = new SecretKeySpec(
                keyBytes,
                "HmacSHA256"
        );

        // Khởi tạo JWT Encoder đúng API Spring Security 6
        this.jwtEncoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(secretKey)
        );

        // Khởi tạo JWT Decoder để xác thực token
        this.jwtDecoder = NimbusJwtDecoder
                .withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();

        this.expiration = expiration;
    }

    // Sinh JWT cho người dùng
    public String generateToken(String userName) {

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("hello-spring-boot")
                .subject(userName)
                .issuedAt(now)
                .expiresAt(now.plusMillis(expiration))
                .claim("scope", "api")
                .build();

        JwsHeader header = JwsHeader
                .with(MacAlgorithm.HS256)
                .build();

        Jwt jwt = jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims)
        );

        return jwt.getTokenValue();
    }

    // Cung cấp Decoder cho SecurityConfig
    public JwtDecoder getJwtDecoder() {

        return jwtDecoder;
    }
    public boolean validateToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);

            return jwt.getSubject() != null
                    && !jwt.getSubject().isBlank();

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}