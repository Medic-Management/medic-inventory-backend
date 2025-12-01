-- Script para crear la tabla de auditoría
CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NULL,
    usuario_nombre VARCHAR(255) NULL,
    accion VARCHAR(100) NOT NULL,
    entidad_tipo VARCHAR(50) NULL,
    entidad_id BIGINT NULL,
    descripcion TEXT NULL,
    ip_address VARCHAR(45) NULL,
    fecha_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_usuario_id (usuario_id),
    INDEX idx_accion (accion),
    INDEX idx_entidad_tipo (entidad_tipo),
    INDEX idx_fecha_hora (fecha_hora)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
