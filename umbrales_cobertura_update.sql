-- HU-17: Sistema de Cobertura Temporal
-- Script para agregar campo de umbral de cobertura en días

-- Agregar campo umbral_cobertura_dias a UMBRAL_STOCK
ALTER TABLE UMBRAL_STOCK
ADD COLUMN umbral_cobertura_dias INT DEFAULT 15 COMMENT 'Días mínimos de cobertura antes de alertar (HU-17)';

-- Crear índice para consultas de cobertura
CREATE INDEX idx_umbrales_cobertura ON UMBRAL_STOCK(umbral_cobertura_dias);

-- Actualizar umbrales existentes con valor por defecto de 15 días
UPDATE UMBRAL_STOCK
SET umbral_cobertura_dias = 15
WHERE umbral_cobertura_dias IS NULL;

-- Comentarios explicativos
ALTER TABLE UMBRAL_STOCK
  MODIFY COLUMN umbral_cobertura_dias INT DEFAULT 15 COMMENT 'HU-17: Días mínimos de cobertura (stock/consumo_diario). Alerta si cobertura < umbral';

-- Script de verificación
-- SELECT u.*, p.nombre as producto_nombre
-- FROM UMBRAL_STOCK u
-- JOIN PRODUCTS p ON u.producto_id = p.id
-- WHERE u.umbral_cobertura_dias IS NOT NULL;
