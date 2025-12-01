package com.medic.inventory.controller;

import com.medic.inventory.dto.AuditLogResponse;
import com.medic.inventory.entity.AuditLog;
import com.medic.inventory.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
@PreAuthorize("hasAuthority('Administrador')")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<List<AuditLogResponse>> getAllAuditLogs() {
        List<AuditLog> logs = auditLogService.getAllAuditLogs();
        List<AuditLogResponse> response = logs.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogsWithFilters(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String accion,
            @RequestParam(required = false) String entidadTipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHasta) {

        List<AuditLog> logs = auditLogService.getAuditLogsWithFilters(
            usuarioId, accion, entidadTipo, fechaDesde, fechaHasta);

        List<AuditLogResponse> response = logs.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    private AuditLogResponse mapToResponse(AuditLog log) {
        AuditLogResponse response = new AuditLogResponse();
        response.setId(log.getId());
        response.setUsuarioId(log.getUsuarioId());
        response.setUsuarioNombre(log.getUsuarioNombre());
        response.setAccion(log.getAccion());
        response.setEntidadTipo(log.getEntidadTipo());
        response.setEntidadId(log.getEntidadId());
        response.setDescripcion(log.getDescripcion());
        response.setIpAddress(log.getIpAddress());
        response.setFechaHora(log.getFechaHora());
        return response;
    }
}
