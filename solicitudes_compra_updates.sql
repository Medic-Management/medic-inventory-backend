-- Script para actualizar tabla SOLICITUDES_COMPRA con nuevas funcionalidades de ÉPICA 2

-- Agregar columnas para gestión avanzada de pedidos
ALTER TABLE SOLICITUDES_COMPRA
ADD COLUMN generado_automaticamente TINYINT(1) DEFAULT 0 AFTER email_body,
ADD COLUMN cantidad_ajustada INT NULL AFTER generado_automaticamente,
ADD COLUMN motivo_ajuste VARCHAR(255) NULL AFTER cantidad_ajustada,
ADD COLUMN aprobado_por BIGINT NULL AFTER motivo_ajuste,
ADD COLUMN fecha_aprobacion DATETIME NULL AFTER aprobado_por;

-- Nota: Los estados DRAFT y SEND_FAILED ya están manejados por el ENUM en código Java
-- Si necesitas consultarlos en SQL, usa: status = 'DRAFT' o status = 'SEND_FAILED'

-- Índices para mejorar performance de consultas
CREATE INDEX idx_solicitudes_status ON SOLICITUDES_COMPRA(status);
CREATE INDEX idx_solicitudes_generado_auto ON SOLICITUDES_COMPRA(generado_automaticamente);
CREATE INDEX idx_solicitudes_aprobado_por ON SOLICITUDES_COMPRA(aprobado_por);

-- Comentarios explicativos
ALTER TABLE SOLICITUDES_COMPRA
  MODIFY COLUMN generado_automaticamente TINYINT(1) DEFAULT 0 COMMENT 'Indica si el pedido fue generado automáticamente por el sistema (HU-04)',
  MODIFY COLUMN cantidad_ajustada INT NULL COMMENT 'Cantidad ajustada automáticamente según stock máximo (HU-04)',
  MODIFY COLUMN motivo_ajuste VARCHAR(255) NULL COMMENT 'Explicación del ajuste realizado a la cantidad',
  MODIFY COLUMN aprobado_por BIGINT NULL COMMENT 'ID del usuario que aprobó el borrador (HU-08)',
  MODIFY COLUMN fecha_aprobacion DATETIME NULL COMMENT 'Fecha y hora de aprobación del pedido';
