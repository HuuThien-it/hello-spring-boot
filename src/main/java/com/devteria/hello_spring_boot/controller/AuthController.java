
package com.devteria.hello_spring_boot.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AuthController {

    @GetMapping("/auth")
    public Map<String, Object> auth(
            Authentication authentication) {

        JwtAuthenticationToken jwtAuth =
                (JwtAuthenticationToken) authentication;

        return Map.of(
                "message", "Token hợp lệ",
                "userName", jwtAuth.getToken().getSubject(),
                "expiresAt",
                jwtAuth.getToken().getExpiresAt().toString()
        );
    }
}