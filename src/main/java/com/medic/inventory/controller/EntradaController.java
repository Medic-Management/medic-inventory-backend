package com.medic.inventory.controller;

import com.medic.inventory.dto.EntradaRequest;
import com.medic.inventory.dto.EntradaResponse;
import com.medic.inventory.entity.User;
import com.medic.inventory.repository.UserRepository;
import com.medic.inventory.service.EntradaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/entradas")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
public class EntradaController {

    private final EntradaService entradaService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<EntradaResponse> registrarEntrada(
            @Valid @RequestBody EntradaRequest request,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            EntradaResponse response = entradaService.registrarEntrada(request, user.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
