package com.medic.inventory.dto;

import com.medic.inventory.entity.RestockRequest;
import com.medic.inventory.entity.RestockRequest.RestockStatus;

import java.time.LocalDateTime;

public class RestockRequestResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Long supplierId;
    private String supplierName;
    private String supplierEmail;
    private Integer requestedQuantity;
    private Integer currentStock;
    private Integer alertLevel;
    private RestockStatus status;
    private LocalDateTime requestDate;
    private LocalDateTime expectedDelivery;
    private LocalDateTime actualDelivery;
    private String notes;
    private Boolean emailSent;
    private String emailSubject;
    private String emailBody;

    public RestockRequestResponse(RestockRequest request) {
        this.id = request.getId();
        this.productId = request.getProduct() != null ? request.getProduct().getId() : null;
        this.productName = request.getProduct() != null ? request.getProduct().getName() : null;
        this.supplierId = request.getSupplier() != null ? request.getSupplier().getId() : null;
        this.supplierName = request.getSupplier() != null ? request.getSupplier().getName() : null;
        this.supplierEmail = request.getSupplier() != null ? request.getSupplier().getEmail() : null;
        this.requestedQuantity = request.getRequestedQuantity();
        this.currentStock = request.getCurrentStock();
        this.alertLevel = request.getAlertLevel();
        this.status = request.getStatus();
        this.requestDate = request.getRequestDate();
        this.expectedDelivery = request.getExpectedDelivery();
        this.actualDelivery = request.getActualDelivery();
        this.notes = request.getNotes();
        this.emailSent = request.getEmailSent();
        this.emailSubject = request.getEmailSubject();
        this.emailBody = request.getEmailBody();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getSupplierEmail() { return supplierEmail; }
    public void setSupplierEmail(String supplierEmail) { this.supplierEmail = supplierEmail; }

    public Integer getRequestedQuantity() { return requestedQuantity; }
    public void setRequestedQuantity(Integer requestedQuantity) { this.requestedQuantity = requestedQuantity; }

    public Integer getCurrentStock() { return currentStock; }
    public void setCurrentStock(Integer currentStock) { this.currentStock = currentStock; }

    public Integer getAlertLevel() { return alertLevel; }
    public void setAlertLevel(Integer alertLevel) { this.alertLevel = alertLevel; }

    public RestockStatus getStatus() { return status; }
    public void setStatus(RestockStatus status) { this.status = status; }

    public LocalDateTime getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }

    public LocalDateTime getExpectedDelivery() { return expectedDelivery; }
    public void setExpectedDelivery(LocalDateTime expectedDelivery) { this.expectedDelivery = expectedDelivery; }

    public LocalDateTime getActualDelivery() { return actualDelivery; }
    public void setActualDelivery(LocalDateTime actualDelivery) { this.actualDelivery = actualDelivery; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Boolean getEmailSent() { return emailSent; }
    public void setEmailSent(Boolean emailSent) { this.emailSent = emailSent; }

    public String getEmailSubject() { return emailSubject; }
    public void setEmailSubject(String emailSubject) { this.emailSubject = emailSubject; }

    public String getEmailBody() { return emailBody; }
    public void setEmailBody(String emailBody) { this.emailBody = emailBody; }
}
