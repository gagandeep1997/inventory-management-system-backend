package com.gagan.inventory.service;

import com.gagan.inventory.dto.request.LoginRequest;
import com.gagan.inventory.dto.request.RegisterRequest;
import com.gagan.inventory.dto.response.AuthResponse;
import com.gagan.inventory.entity.User;
import com.gagan.inventory.exception.ResourceAlreadyExistsException;
import com.gagan.inventory.exception.ResourceNotFoundException;
import com.gagan.inventory.repository.UserRepository;
import com.gagan.inventory.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("User", "email", request.getEmail());
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);

        return AuthResponse.builder()
                .message("User registered successfully")
                .email(savedUser.getEmail())
                .role(savedUser.getRole().name())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user =
                userRepository
                        .findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User",
                                        "email",
                                        request.getEmail()
                                )
                        );

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!passwordMatches) {
            throw new IllegalArgumentException(
                    "Invalid email or password"
            );
        }

        String jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .message("login successful!")
                .email(user.getEmail())
                .role(user.getRole().name())
                .token(jwtToken)
                .build();
    }
}
