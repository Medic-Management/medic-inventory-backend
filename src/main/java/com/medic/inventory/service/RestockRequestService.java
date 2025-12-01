package com.medic.inventory.service;

import com.medic.inventory.dto.RestockRequestRequest;
import com.medic.inventory.entity.Product;
import com.medic.inventory.entity.RestockRequest;
import com.medic.inventory.entity.RestockRequest.RestockStatus;
import com.medic.inventory.entity.Supplier;
import com.medic.inventory.repository.ProductRepository;
import com.medic.inventory.repository.RestockRequestRepository;
import com.medic.inventory.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestockRequestService {

    private static final Logger logger = LoggerFactory.getLogger(RestockRequestService.class);

    @Autowired
    private RestockRequestRepository restockRequestRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    public List<RestockRequest> getAllRestockRequests() {
        return restockRequestRepository.findAll();
    }

    public Optional<RestockRequest> getRestockRequestById(Long id) {
        return restockRequestRepository.findById(id);
    }

    public List<RestockRequest> getActiveRequests() {
        return restockRequestRepository.findActiveRequests();
    }

    public List<RestockRequest> getRequestsByProduct(Long productId) {
        return restockRequestRepository.findByProductId(productId);
    }

    public List<RestockRequest> getRequestsBySupplier(Long supplierId) {
        return restockRequestRepository.findBySupplierId(supplierId);
    }

    public List<RestockRequest> getPendingEmailRequests() {
        logger.info("Fetching pending email requests");
        return restockRequestRepository.findAll().stream()
                .filter(r -> r.getEmailSent() == null || !r.getEmailSent())
                .filter(r -> r.getStatus() == RestockStatus.PENDING)
                .toList();
    }

    @Transactional
    public RestockRequest createRestockRequestFromDto(RestockRequestRequest dto) {
        logger.info("Creating restock request from DTO - Product ID: {}, Supplier ID: {}",
                dto.getProductId(), dto.getSupplierId());

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.getProductId()));

        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + dto.getSupplierId()));

        RestockRequest request = new RestockRequest();
        request.setProduct(product);
        request.setSupplier(supplier);
        request.setRequestedQuantity(dto.getRequestedQuantity());
        request.setCurrentStock(product.getQuantity());
        request.setAlertLevel(product.getAlertValue());
        request.setNotes(dto.getNotes());
        request.setStatus(RestockStatus.PENDING);
        request.setEmailSent(false);

        request.setEmailSubject("Solicitud de reabastecimiento - " + product.getName());
        request.setEmailBody(generateDefaultEmailBody(product, dto.getRequestedQuantity(), product.getQuantity(), product.getAlertValue()));

        return createRestockRequest(request);
    }

    @Transactional
    public RestockRequest queueEmailForUiPath(Long id) {
        logger.info("Queuing email for UiPath - restock request ID: {}", id);

        return restockRequestRepository.findById(id)
                .map(request -> {
                    request.setEmailSent(false);
                    request.setStatus(RestockStatus.PENDING);
                    RestockRequest saved = restockRequestRepository.save(request);
                    logger.info("Email queued successfully for UiPath - request ID: {}", id);
                    return saved;
                })
                .orElseThrow(() -> {
                    logger.error("Restock request not found with id: {}", id);
                    return new RuntimeException("Restock request not found with id: " + id);
                });
    }

    @Transactional
    public RestockRequest updateEmailContent(Long id, String emailSubject, String emailBody) {
        logger.info("Updating email content for restock request ID: {}", id);

        return restockRequestRepository.findById(id)
                .map(request -> {
                    request.setEmailSubject(emailSubject);
                    request.setEmailBody(emailBody);
                    RestockRequest saved = restockRequestRepository.save(request);
                    logger.info("Email content updated successfully for request ID: {}", id);
                    return saved;
                })
                .orElseThrow(() -> {
                    logger.error("Restock request not found with id: {}", id);
                    return new RuntimeException("Restock request not found with id: " + id);
                });
    }

    @Transactional
    public RestockRequest markEmailSent(Long id) {
        logger.info("Marking email as sent for restock request ID: {}", id);

        return restockRequestRepository.findById(id)
                .map(request -> {
                    request.setEmailSent(true);
                    request.setStatus(RestockStatus.SENT);
                    RestockRequest saved = restockRequestRepository.save(request);
                    logger.info("Email marked as sent successfully for request ID: {}", id);
                    return saved;
                })
                .orElseThrow(() -> {
                    logger.error("Restock request not found with id: {}", id);
                    return new RuntimeException("Restock request not found with id: " + id);
                });
    }

    @Transactional
    public RestockRequest createRestockRequest(RestockRequest request) {
        if (request.getRequestDate() == null) {
            request.setRequestDate(LocalDateTime.now());
        }

        if (request.getExpectedDelivery() == null) {
            request.setExpectedDelivery(LocalDateTime.now().plusDays(7));
        }

        RestockRequest saved = restockRequestRepository.save(request);

        sendRestockEmail(saved);

        return saved;
    }

    @Transactional
    public RestockRequest updateRestockStatus(Long id, RestockStatus status) {
        return restockRequestRepository.findById(id)
                .map(request -> {
                    request.setStatus(status);

                    if (status == RestockStatus.DELIVERED) {
                        request.setActualDelivery(LocalDateTime.now());
                        updateProductStock(request);
                    }

                    return restockRequestRepository.save(request);
                })
                .orElseThrow(() -> new RuntimeException("Restock request not found with id: " + id));
    }

    @Transactional
    public List<RestockRequest> autoCheckAndRestock() {
        List<Product> lowStockProducts = productRepository.findAll().stream()
                .filter(p -> p.getAlertValue() != null && p.getQuantity() <= p.getAlertValue())
                .toList();

        return lowStockProducts.stream()
                .map(this::createAutoRestockRequest)
                .toList();
    }

    private RestockRequest createAutoRestockRequest(Product product) {
        RestockRequest request = new RestockRequest();
        request.setProduct(product);
        request.setSupplier(product.getSupplier());
        request.setCurrentStock(product.getQuantity());
        request.setAlertLevel(product.getAlertValue());

        Integer requestedQty = product.getInitialStock() - product.getQuantity();
        request.setRequestedQuantity(Math.max(requestedQty, product.getAlertValue() * 2));

        request.setStatus(RestockStatus.PENDING);
        request.setNotes("Automatic restock request - low stock alert");

        return createRestockRequest(request);
    }

    private void sendRestockEmail(RestockRequest request) {
        request.setEmailSent(false);
        request.setStatus(RestockStatus.PENDING);
        restockRequestRepository.save(request);

        logger.info("=== RESTOCK EMAIL PENDING ===");
        logger.info("Restock Request ID: {}", request.getId());
        logger.info("To: {}", request.getSupplier().getEmail());
        logger.info("Subject: Restock Request for {}", request.getProduct().getName());
        logger.info("Product: {}", request.getProduct().getName());
        logger.info("Quantity: {}", request.getRequestedQuantity());
        logger.info("Expected Delivery: {}", request.getExpectedDelivery());
        logger.info("Status: PENDING - Waiting for UiPath to send email");
        logger.info("=============================");
    }

    private void updateProductStock(RestockRequest request) {
        Product product = request.getProduct();
        product.setQuantity(product.getQuantity() + request.getRequestedQuantity());
        product.setInTransit(Math.max(0, product.getInTransit() - request.getRequestedQuantity()));
        productRepository.save(product);
    }

    @Transactional
    public void deleteRestockRequest(Long id) {
        restockRequestRepository.deleteById(id);
    }

    private String generateDefaultEmailBody(Product product, Integer requestedQuantity, Integer currentStock, Integer alertLevel) {
        return String.format(
            "Estimado proveedor,\n\n" +
            "Solicitamos el envío de %d unidades de %s para mantener el nivel de stock óptimo.\n\n" +
            "Stock actual: %d unidades\n" +
            "Nivel de alerta: %d unidades\n\n" +
            "Atentamente,\n" +
            "Clínica Vestida de Sol - Área de Farmacia.",
            requestedQuantity != null ? requestedQuantity : 0,
            product.getName(),
            currentStock != null ? currentStock : 0,
            alertLevel != null ? alertLevel : 0
        );
    }
}
