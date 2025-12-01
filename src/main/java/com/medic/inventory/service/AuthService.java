package com.medic.inventory.service;

import com.medic.inventory.config.JwtUtil;
import com.medic.inventory.dto.LoginRequest;
import com.medic.inventory.dto.LoginResponse;
import com.medic.inventory.entity.User;
import com.medic.inventory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!request.getPassword().equals(user.getPasswordHash())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String roleName = user.getRol() != null ? user.getRol().getNombre() : "USER";

        try {
            roleName = new String(roleName.getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            System.err.println("Error converting role encoding: " + e.getMessage());
        }

        String token = jwtUtil.generateToken(user.getEmail(), roleName);

        if (request.getRememberMe() != null && request.getRememberMe()) {
            user.setRememberToken(token);
            userRepository.save(user);
        }

        LoginResponse response = new LoginResponse();
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getNombreCompleto());
        response.setRole(roleName);
        response.setToken(token);
        response.setMessage("Login exitoso");

        return response;
    }

    @Transactional
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }
}
