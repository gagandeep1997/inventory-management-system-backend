package com.gagan.inventory.service;

import com.gagan.inventory.dto.request.LoginRequest;
import com.gagan.inventory.dto.request.RegisterRequest;
import com.gagan.inventory.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
