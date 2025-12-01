package com.medic.inventory.service;

import com.medic.inventory.entity.AuditLog;
import com.medic.inventory.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Transactional
    public void logAction(Long usuarioId, String usuarioNombre, String accion,
                         String entidadTipo, Long entidadId, String descripcion, String ipAddress) {
        try {
            AuditLog auditLog = new AuditLog();
            auditLog.setUsuarioId(usuarioId);
            auditLog.setUsuarioNombre(usuarioNombre);
            auditLog.setAccion(accion);
            auditLog.setEntidadTipo(entidadTipo);
            auditLog.setEntidadId(entidadId);
            auditLog.setDescripcion(descripcion);
            auditLog.setIpAddress(ipAddress);
            auditLog.setFechaHora(LocalDateTime.now());

            auditLogRepository.save(auditLog);
            log.debug("Audit log created: {} - {} - {}", usuarioNombre, accion, entidadTipo);
        } catch (Exception e) {
            log.error("Error creating audit log", e);
        }
    }

    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAllByOrderByFechaHoraDesc();
    }

    public List<AuditLog> getAuditLogsByUser(Long usuarioId) {
        return auditLogRepository.findByUsuarioIdOrderByFechaHoraDesc(usuarioId);
    }

    public List<AuditLog> getAuditLogsByAccion(String accion) {
        return auditLogRepository.findByAccionOrderByFechaHoraDesc(accion);
    }

    public List<AuditLog> getAuditLogsByEntidadTipo(String entidadTipo) {
        return auditLogRepository.findByEntidadTipoOrderByFechaHoraDesc(entidadTipo);
    }

    public List<AuditLog> getAuditLogsWithFilters(
            Long usuarioId, String accion, String entidadTipo,
            LocalDateTime fechaDesde, LocalDateTime fechaHasta) {
        return auditLogRepository.findByFilters(usuarioId, accion, entidadTipo, fechaDesde, fechaHasta);
    }
}
