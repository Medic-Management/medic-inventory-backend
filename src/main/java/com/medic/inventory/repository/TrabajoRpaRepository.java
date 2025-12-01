package com.medic.inventory.repository;

import com.medic.inventory.entity.TrabajoRpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrabajoRpaRepository extends JpaRepository<TrabajoRpa, Long> {
    Optional<TrabajoRpa> findBySolicitudId(Long solicitudId);

    List<TrabajoRpa> findByEstado(String estado);

    @Query("SELECT t FROM TrabajoRpa t WHERE t.estado IN ('PENDING', 'RUNNING') ORDER BY t.solicitadoEn ASC")
    List<TrabajoRpa> findTrabajosActivos();
}
