package com.medic.inventory.controller;

import com.medic.inventory.dto.RestockRequestRequest;
import com.medic.inventory.dto.RestockRequestResponse;
import com.medic.inventory.entity.RestockRequest;
import com.medic.inventory.entity.RestockRequest.RestockStatus;
import com.medic.inventory.service.RestockRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restock")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
public class RestockRequestController {

    @Autowired
    private RestockRequestService restockRequestService;

    @GetMapping
    public ResponseEntity<List<RestockRequestResponse>> getAllRestockRequests() {
        List<RestockRequestResponse> responses = restockRequestService.getAllRestockRequests()
                .stream()
                .map(RestockRequestResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestockRequestResponse> getRestockRequestById(@PathVariable Long id) {
        return restockRequestService.getRestockRequestById(id)
                .map(RestockRequestResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<List<RestockRequestResponse>> getActiveRequests() {
        List<RestockRequestResponse> responses = restockRequestService.getActiveRequests()
                .stream()
                .map(RestockRequestResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/pending-email")
    public ResponseEntity<List<RestockRequestResponse>> getPendingEmailRequests() {
        List<RestockRequestResponse> responses = restockRequestService.getPendingEmailRequests()
                .stream()
                .map(RestockRequestResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<RestockRequest>> getRequestsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(restockRequestService.getRequestsByProduct(productId));
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<RestockRequest>> getRequestsBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(restockRequestService.getRequestsBySupplier(supplierId));
    }

    @PostMapping("/request")
    public ResponseEntity<RestockRequestResponse> createRestockRequest(@RequestBody RestockRequestRequest requestDto) {
        RestockRequest createdRequest = restockRequestService.createRestockRequestFromDto(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RestockRequestResponse(createdRequest));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<RestockRequestResponse> updateRestockStatus(
            @PathVariable Long id,
            @RequestBody RestockStatusRequest statusRequest) {
        try {
            RestockRequest updatedRequest = restockRequestService.updateRestockStatus(id, statusRequest.getStatus());
            return ResponseEntity.ok(new RestockRequestResponse(updatedRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/queue-email")
    public ResponseEntity<RestockRequestResponse> queueEmailForUiPath(@PathVariable Long id) {
        try {
            RestockRequest updatedRequest = restockRequestService.queueEmailForUiPath(id);
            return ResponseEntity.ok(new RestockRequestResponse(updatedRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/email-content")
    public ResponseEntity<RestockRequestResponse> updateEmailContent(
            @PathVariable Long id,
            @RequestBody EmailContentRequest emailContentRequest) {
        try {
            RestockRequest updatedRequest = restockRequestService.updateEmailContent(
                    id,
                    emailContentRequest.getEmailSubject(),
                    emailContentRequest.getEmailBody()
            );
            return ResponseEntity.ok(new RestockRequestResponse(updatedRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/email-sent")
    public ResponseEntity<RestockRequestResponse> markEmailSent(@PathVariable Long id) {
        try {
            RestockRequest updatedRequest = restockRequestService.markEmailSent(id);
            return ResponseEntity.ok(new RestockRequestResponse(updatedRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/auto-check")
    public ResponseEntity<List<RestockRequestResponse>> autoCheckAndRestock() {
        List<RestockRequestResponse> responses = restockRequestService.autoCheckAndRestock()
                .stream()
                .map(RestockRequestResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestockRequest(@PathVariable Long id) {
        restockRequestService.deleteRestockRequest(id);
        return ResponseEntity.noContent().build();
    }

    public static class RestockStatusRequest {
        private RestockStatus status;

        public RestockStatus getStatus() {
            return status;
        }

        public void setStatus(RestockStatus status) {
            this.status = status;
        }
    }

    public static class EmailContentRequest {
        private String emailSubject;
        private String emailBody;

        public String getEmailSubject() {
            return emailSubject;
        }

        public void setEmailSubject(String emailSubject) {
            this.emailSubject = emailSubject;
        }

        public String getEmailBody() {
            return emailBody;
        }

        public void setEmailBody(String emailBody) {
            this.emailBody = emailBody;
        }
    }
}
