package com.medic.inventory.service;

import com.medic.inventory.dto.TrabajoRpaRequest;
import com.medic.inventory.dto.TrabajoRpaResponse;
import com.medic.inventory.dto.UiPathAlertaDTO;
import com.medic.inventory.entity.Alerta;
import com.medic.inventory.entity.TrabajoRpa;
import com.medic.inventory.repository.AlertaRepository;
import com.medic.inventory.repository.TrabajoRpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrabajoRpaService {

    private final TrabajoRpaRepository trabajoRpaRepository;
    private final AlertaRepository alertaRepository;

    public List<UiPathAlertaDTO> obtenerAlertasParaEmail() {
        List<Alerta> alertas = alertaRepository.findByActivaOrderByDisparadaEnDesc(1);

        return alertas.stream()
            .filter(alerta -> !tieneTrabajoRpaPendiente(alerta.getId()))
            .map(this::mapToUiPathDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public TrabajoRpaResponse registrarTrabajoRpa(TrabajoRpaRequest request) {
        TrabajoRpa trabajo = new TrabajoRpa();
        trabajo.setSolicitudId(request.getAlertaId());
        trabajo.setOrchestratorJobId(request.getOrchestratorJobId());
        trabajo.setEmailMessageId(request.getEmailMessageId());
        trabajo.setEstado("ENVIADO");
        trabajo.setSolicitadoEn(LocalDateTime.now());

        trabajo = trabajoRpaRepository.save(trabajo);
        return mapToResponse(trabajo);
    }

    @Transactional
    public void marcarTrabajoCompletado(Long trabajoId, String estado) {
        TrabajoRpa trabajo = trabajoRpaRepository.findById(trabajoId)
            .orElseThrow(() -> new RuntimeException("Trabajo RPA no encontrado"));

        trabajo.setEstado(estado);
        trabajo.setFinalizadoEn(LocalDateTime.now());
        trabajoRpaRepository.save(trabajo);
    }

    public List<TrabajoRpaResponse> obtenerTodosLosTrabajos() {
        return trabajoRpaRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    private boolean tieneTrabajoRpaPendiente(Long alertaId) {
        return trabajoRpaRepository.findAll().stream()
            .anyMatch(t -> t.getSolicitudId().equals(alertaId) &&
                          "ENVIADO".equals(t.getEstado()));
    }

    private UiPathAlertaDTO mapToUiPathDTO(Alerta alerta) {
        UiPathAlertaDTO dto = new UiPathAlertaDTO();
        dto.setAlertaId(alerta.getId());
        dto.setProductoNombre(alerta.getProducto().getNombre());
        dto.setSedeNombre(alerta.getSede().getNombre());
        dto.setTipo(alerta.getTipo());
        dto.setNivel(alerta.getNivel());

        dto.setStockActual(0);
        dto.setNivelMinimo(0);

        dto.setEmailDestinatario("farmacia@hospital.com"); // Configurar según sede

        String mensaje = String.format(
            "ALERTA DE STOCK - %s\n\nProducto: %s\nSede: %s\nTipo: %s\nNivel: %s\n\nPor favor, proceder con reabastecimiento.",
            alerta.getTipo(),
            alerta.getProducto().getNombre(),
            alerta.getSede().getNombre(),
            alerta.getTipo(),
            alerta.getNivel()
        );
        dto.setMensaje(mensaje);

        return dto;
    }

    private TrabajoRpaResponse mapToResponse(TrabajoRpa trabajo) {
        TrabajoRpaResponse response = new TrabajoRpaResponse();
        response.setId(trabajo.getId());
        response.setSolicitudId(trabajo.getSolicitudId());
        response.setOrchestratorJobId(trabajo.getOrchestratorJobId());
        response.setEmailMessageId(trabajo.getEmailMessageId());
        response.setEstado(trabajo.getEstado());
        response.setSolicitadoEn(trabajo.getSolicitadoEn());
        response.setFinalizadoEn(trabajo.getFinalizadoEn());
        return response;
    }
}
