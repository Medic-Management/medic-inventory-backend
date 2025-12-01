package com.medic.inventory.controller;

import com.medic.inventory.dto.SettingsRequest;
import com.medic.inventory.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4300", "http://localhost:4200"})
public class SettingsController {
    private final SettingsService settingsService;

    @GetMapping("/{userId}")
    public ResponseEntity<SettingsRequest> getUserSettings(@PathVariable Long userId) {
        SettingsRequest settings = settingsService.getUserSettings(userId);
        return ResponseEntity.ok(settings);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<SettingsRequest> updateSettings(
            @PathVariable Long userId,
            @RequestBody SettingsRequest request) {
        SettingsRequest updatedSettings = settingsService.updateSettings(userId, request);
        return ResponseEntity.ok(updatedSettings);
    }
}
