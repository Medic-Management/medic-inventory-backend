package com.medic.inventory.controller;

import com.medic.inventory.dto.UserRequest;
import com.medic.inventory.dto.UserResponse;
import com.medic.inventory.entity.User;
import com.medic.inventory.repository.UserRepository;
import com.medic.inventory.service.UserManagementService;
import com.medic.inventory.service.AuditLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
public class UserManagementController {

    private final UserManagementService userManagementService;
    private final AuditLogService auditLogService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userManagementService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userManagementService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest) {
        try {
            UserResponse user = userManagementService.createUser(request);

            String email = authentication.getName();
            User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            String ipAddress = getClientIpAddress(httpRequest);
            auditLogService.logAction(
                currentUser.getId(),
                currentUser.getNombreCompleto(),
                "USUARIO_CREADO",
                "Usuario",
                user.getId(),
                String.format("Usuario creado: %s (%s)", user.getNombreCompleto(), user.getEmail()),
                ipAddress
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest) {
        try {
            UserResponse user = userManagementService.updateUser(id, request);

            String email = authentication.getName();
            User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            String ipAddress = getClientIpAddress(httpRequest);
            auditLogService.logAction(
                currentUser.getId(),
                currentUser.getNombreCompleto(),
                "USUARIO_ACTUALIZADO",
                "Usuario",
                user.getId(),
                String.format("Usuario actualizado: %s", user.getNombreCompleto()),
                ipAddress
            );

            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<Void> toggleUserStatus(
            @PathVariable Long id,
            Authentication authentication,
            HttpServletRequest httpRequest) {
        UserResponse user = userManagementService.getUserById(id);
        userManagementService.toggleUserStatus(id);

        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String ipAddress = getClientIpAddress(httpRequest);
        auditLogService.logAction(
            currentUser.getId(),
            currentUser.getNombreCompleto(),
            "USUARIO_ESTADO_CAMBIADO",
            "Usuario",
            id,
            String.format("Estado de usuario cambiado: %s", user.getNombreCompleto()),
            ipAddress
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/roles")
    public ResponseEntity<List<UserResponse>> getAllRoles() {
        List<UserResponse> roles = userManagementService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        } else {
            return xForwardedForHeader.split(",")[0];
        }
    }
}
