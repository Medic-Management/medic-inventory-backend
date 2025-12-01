package com.medic.inventory.dto;

import lombok.Data;

@Data
public class UiPathAlertaDTO {
    private Long alertaId;
    private String productoNombre;
    private String sedeNombre;
    private Integer stockActual;
    private Integer nivelMinimo;
    private String tipo;
    private String nivel;
    private String emailDestinatario;
    private String mensaje;
}
