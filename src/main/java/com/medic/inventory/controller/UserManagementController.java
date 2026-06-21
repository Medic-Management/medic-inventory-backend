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
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest) {
        UserResponse user;
        try {
            user = userManagementService.createUser(request);
        } catch (RuntimeException e) {
            // HU-15: Retornar mensaje específico de error (ej: email duplicado)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(java.util.Map.of("message", e.getMessage()));
        }

        // CP15: la auditoría NO debe afectar el resultado de la creación del usuario
        try {
            String email = authentication != null ? authentication.getName() : null;
            if (email != null) {
                userRepository.findByEmail(email).ifPresent(currentUser ->
                    auditLogService.logAction(
                        currentUser.getId(),
                        currentUser.getNombreCompleto(),
                        "USUARIO_CREADO",
                        "Usuario",
                        user.getId(),
                        String.format("Usuario creado: %s (%s)", user.getNombreCompleto(), user.getEmail()),
                        getClientIpAddress(httpRequest)
                    ));
            }
        } catch (Exception ignore) {
            // un fallo de auditoría no debe romper la creación ya realizada
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest) {
        UserResponse user;
        try {
            user = userManagementService.updateUser(id, request);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }

        // CP15: la auditoría no debe afectar el resultado de la actualización
        try {
            String email = authentication != null ? authentication.getName() : null;
            if (email != null) {
                final UserResponse u = user;
                userRepository.findByEmail(email).ifPresent(currentUser ->
                    auditLogService.logAction(
                        currentUser.getId(),
                        currentUser.getNombreCompleto(),
                        "USUARIO_ACTUALIZADO",
                        "Usuario",
                        u.getId(),
                        String.format("Usuario actualizado: %s", u.getNombreCompleto()),
                        getClientIpAddress(httpRequest)
                    ));
            }
        } catch (Exception ignore) { }

        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<?> toggleUserStatus(
            @PathVariable Long id,
            Authentication authentication,
            HttpServletRequest httpRequest) {
        UserResponse user;
        try {
            user = userManagementService.getUserById(id);
            userManagementService.toggleUserStatus(id);
        } catch (RuntimeException e) {
            // HU-13 Escenario 2: Retornar mensaje específico si es el último administrador
            if (e.getMessage().contains("ULTIMO_ADMINISTRADOR")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(java.util.Map.of("message", e.getMessage().replace("ULTIMO_ADMINISTRADOR: ", "")));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(java.util.Map.of("message", e.getMessage()));
        }

        // CP15: la auditoría no debe afectar el resultado del cambio de estado
        try {
            String email = authentication != null ? authentication.getName() : null;
            if (email != null) {
                final UserResponse u = user;
                userRepository.findByEmail(email).ifPresent(currentUser ->
                    auditLogService.logAction(
                        currentUser.getId(),
                        currentUser.getNombreCompleto(),
                        "USUARIO_ESTADO_CAMBIADO",
                        "Usuario",
                        id,
                        String.format("Estado de usuario cambiado: %s", u.getNombreCompleto()),
                        getClientIpAddress(httpRequest)
                    ));
            }
        } catch (Exception ignore) { }

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
