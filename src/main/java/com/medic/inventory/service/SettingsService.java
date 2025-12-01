package com.medic.inventory.service;

import com.medic.inventory.dto.SettingsRequest;
import com.medic.inventory.entity.Settings;
import com.medic.inventory.entity.User;
import com.medic.inventory.repository.SettingsRepository;
import com.medic.inventory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SettingsService {
    private final SettingsRepository settingsRepository;
    private final UserRepository userRepository;

    public SettingsRequest getUserSettings(Long userId) {
        Settings settings = settingsRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultSettings(userId));

        return convertToDTO(settings);
    }

    @Transactional
    public SettingsRequest updateSettings(Long userId, SettingsRequest request) {
        Settings settings = settingsRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultSettings(userId));

        settings.setLanguage(request.getLanguage());
        settings.setTimezone(request.getTimezone());
        settings.setDateFormat(request.getDateFormat());

        if (request.getNotifications() != null) {
            settings.setNotifyLowStock(request.getNotifications().getLowStock());
            settings.setNotifyExpiring(request.getNotifications().getExpiring());
            settings.setNotifyNewOrders(request.getNotifications().getNewOrders());
            settings.setNotifyByEmail(request.getNotifications().getEmail());
        }

        if (request.getInventory() != null) {
            settings.setAlertValue(request.getInventory().getAlertValue());
            settings.setExpirationDays(request.getInventory().getExpirationDays());
            settings.setAutoUpdate(request.getInventory().getAutoUpdate());
        }

        Settings savedSettings = settingsRepository.save(settings);
        return convertToDTO(savedSettings);
    }

    private Settings createDefaultSettings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Settings settings = new Settings();
        settings.setUser(user);
        return settingsRepository.save(settings);
    }

    private SettingsRequest convertToDTO(Settings settings) {
        SettingsRequest dto = new SettingsRequest();
        dto.setLanguage(settings.getLanguage());
        dto.setTimezone(settings.getTimezone());
        dto.setDateFormat(settings.getDateFormat());

        SettingsRequest.NotificationSettings notifications = new SettingsRequest.NotificationSettings();
        notifications.setLowStock(settings.getNotifyLowStock());
        notifications.setExpiring(settings.getNotifyExpiring());
        notifications.setNewOrders(settings.getNotifyNewOrders());
        notifications.setEmail(settings.getNotifyByEmail());
        dto.setNotifications(notifications);

        SettingsRequest.InventorySettings inventory = new SettingsRequest.InventorySettings();
        inventory.setAlertValue(settings.getAlertValue());
        inventory.setExpirationDays(settings.getExpirationDays());
        inventory.setAutoUpdate(settings.getAutoUpdate());
        dto.setInventory(inventory);

        return dto;
    }
}
