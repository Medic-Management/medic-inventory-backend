package com.medic.inventory.service;

import com.medic.inventory.dto.UserRequest;
import com.medic.inventory.dto.UserResponse;
import com.medic.inventory.entity.Role;
import com.medic.inventory.entity.User;
import com.medic.inventory.repository.RoleRepository;
import com.medic.inventory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mapToResponse(user);
    }

    @Transactional
    public UserResponse createUser(UserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        Role role = roleRepository.findById(request.getRolId())
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new RuntimeException("La contraseña es obligatoria");
        }

        User user = new User();
        user.setNombreCompleto(request.getNombreCompleto());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRol(role);
        user.setActivo(1);

        user = userRepository.save(user);
        return mapToResponse(user);
    }

    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        userRepository.findByEmail(request.getEmail()).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(id)) {
                throw new RuntimeException("El email ya está registrado");
            }
        });

        Role role = roleRepository.findById(request.getRolId())
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        user.setNombreCompleto(request.getNombreCompleto());
        user.setEmail(request.getEmail());
        user.setRol(role);

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        user = userRepository.save(user);
        return mapToResponse(user);
    }

    @Transactional
    public void toggleUserStatus(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setActivo(user.getActivo() == 1 ? 0 : 1);
        userRepository.save(user);
    }

    public List<UserResponse> getAllRoles() {
        return roleRepository.findAll().stream()
            .map(role -> {
                UserResponse response = new UserResponse();
                response.setRolId(role.getId());
                response.setRolNombre(role.getNombre());
                return response;
            })
            .collect(Collectors.toList());
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setNombreCompleto(user.getNombreCompleto());
        response.setEmail(user.getEmail());
        response.setActivo(user.getActivo());
        response.setCreadoEn(user.getCreadoEn());

        if (user.getRol() != null) {
            response.setRolId(user.getRol().getId());
            response.setRolNombre(user.getRol().getNombre());
        }

        return response;
    }
}
