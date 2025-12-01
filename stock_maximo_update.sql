-- HU-04.3: Ajuste Automático por Stock Máximo
-- Script para agregar campo de stock máximo a UMBRAL_STOCK

-- Agregar campo stock_maximo a UMBRAL_STOCK
ALTER TABLE UMBRAL_STOCK
ADD COLUMN stock_maximo INT NULL COMMENT 'Stock máximo permitido (HU-04.3). Pedidos automáticos se ajustan para no exceder este límite.';

-- Crear índice para consultas de stock máximo
CREATE INDEX idx_stock_maximo ON UMBRAL_STOCK(stock_maximo);

-- Script de verificación
-- SELECT u.*, p.nombre as producto_nombre
-- FROM UMBRAL_STOCK u
-- JOIN PRODUCTS p ON u.producto_id = p.id
-- WHERE u.stock_maximo IS NOT NULL;
