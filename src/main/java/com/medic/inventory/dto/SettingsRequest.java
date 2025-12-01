package com.medic.inventory.dto;

import lombok.Data;

@Data
public class SettingsRequest {
    private String language;
    private String timezone;
    private String dateFormat;
    private NotificationSettings notifications;
    private InventorySettings inventory;

    @Data
    public static class NotificationSettings {
        private Boolean lowStock;
        private Boolean expiring;
        private Boolean newOrders;
        private Boolean email;
    }

    @Data
    public static class InventorySettings {
        private Integer alertValue;
        private Integer expirationDays;
        private Boolean autoUpdate;
    }
}
