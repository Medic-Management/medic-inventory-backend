package com.medic.inventory.repository;

import com.medic.inventory.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByUsuarioIdOrderByFechaHoraDesc(Long usuarioId);

    List<AuditLog> findByAccionOrderByFechaHoraDesc(String accion);

    List<AuditLog> findByEntidadTipoOrderByFechaHoraDesc(String entidadTipo);

    List<AuditLog> findAllByOrderByFechaHoraDesc();

    @Query("SELECT a FROM AuditLog a WHERE " +
           "(:usuarioId IS NULL OR a.usuarioId = :usuarioId) AND " +
           "(:accion IS NULL OR a.accion = :accion) AND " +
           "(:entidadTipo IS NULL OR a.entidadTipo = :entidadTipo) AND " +
           "(:fechaDesde IS NULL OR a.fechaHora >= :fechaDesde) AND " +
           "(:fechaHasta IS NULL OR a.fechaHora <= :fechaHasta) " +
           "ORDER BY a.fechaHora DESC")
    List<AuditLog> findByFilters(
        @Param("usuarioId") Long usuarioId,
        @Param("accion") String accion,
        @Param("entidadTipo") String entidadTipo,
        @Param("fechaDesde") LocalDateTime fechaDesde,
        @Param("fechaHasta") LocalDateTime fechaHasta
    );
}
