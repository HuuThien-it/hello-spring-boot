
package com.devteria.hello_spring_boot.service;

import com.devteria.hello_spring_boot.dto.LoginRequest;
import com.devteria.hello_spring_boot.dto.LoginResponse;
import com.devteria.hello_spring_boot.model.User;
import com.devteria.hello_spring_boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final long expiration;

    public AuthService(
            UserRepository userRepository,
            JwtService jwtService,
            @Value("${jwt.expiration}") long expiration) {

        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.expiration = expiration;
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUserName(
                request.getUserName());

        if (user == null ||
                !user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Sai username hoặc password");
        }

        String token = jwtService.generateToken(user.getUserName());

        // Lưu token vào đối tượng User theo đề bài
        user.setToken(token);
        userRepository.save(user);

        return new LoginResponse(token, expiration / 1000);
    }
}