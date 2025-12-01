package com.medic.inventory.repository;

import com.medic.inventory.entity.RestockRequest;
import com.medic.inventory.entity.RestockRequest.RestockStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestockRequestRepository extends JpaRepository<RestockRequest, Long> {

    List<RestockRequest> findByStatus(RestockStatus status);

    List<RestockRequest> findByProductId(Long productId);

    List<RestockRequest> findBySupplierId(Long supplierId);

    @Query("SELECT r FROM RestockRequest r WHERE r.status IN ('PENDING', 'SENT', 'CONFIRMED', 'IN_TRANSIT') ORDER BY r.requestDate DESC")
    List<RestockRequest> findActiveRequests();

    @Query("SELECT r FROM RestockRequest r WHERE r.emailSent = false AND r.status = 'PENDING'")
    List<RestockRequest> findPendingEmailRequests();
}
