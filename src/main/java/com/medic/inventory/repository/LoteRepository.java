package com.medic.inventory.repository;

import com.medic.inventory.entity.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {
    List<Lote> findByProductoId(Long productoId);

    @Query("SELECT l FROM Lote l WHERE l.producto.id = :productoId AND l.codigoProductoProv = :codigoLote")
    java.util.Optional<Lote> findByProductoIdAndCodigoProductoProv(Long productoId, String codigoLote);

    @Query("SELECT l FROM Lote l WHERE l.fechaVencimiento <= :fecha AND l.estado = true " +
           "AND (l.bloqueado IS NULL OR l.bloqueado = false) " +
           "AND (l.producto.bloqueado IS NULL OR l.producto.bloqueado = false)")
    List<Lote> findLotesProximosAVencer(LocalDate fecha);

    // HU-11: FEFO - First Expired, First Out
    // CP021: Excluir lotes bloqueados y productos bloqueados
    @Query("SELECT l FROM Lote l WHERE l.producto.id = :productoId AND l.estado = true " +
           "AND (l.bloqueado IS NULL OR l.bloqueado = false) " +
           "AND (l.producto.bloqueado IS NULL OR l.producto.bloqueado = false) " +
           "ORDER BY l.fechaVencimiento ASC")
    List<Lote> findByProductoIdOrderByFechaVencimientoAsc(Long productoId);

    // HU-11: FEFO con filtro por sede
    // CP021: Excluir lotes bloqueados y productos bloqueados
    @Query("SELECT DISTINCT l FROM Lote l " +
           "JOIN Inventario i ON i.loteId = l.id " +
           "WHERE l.producto.id = :productoId " +
           "AND i.sedeId = :sedeId " +
           "AND i.cantidad > 0 " +
           "AND l.estado = true " +
           "AND (l.bloqueado IS NULL OR l.bloqueado = false) " +
           "AND (l.producto.bloqueado IS NULL OR l.producto.bloqueado = false) " +
           "ORDER BY l.fechaVencimiento ASC")
    List<Lote> findByProductoIdAndSedeIdWithStockOrderByFechaVencimiento(Long productoId, Long sedeId);

    // HU-21: Obtener todos los lotes (activos e inactivos) con información de inventario
    @Query("SELECT DISTINCT l FROM Lote l " +
           "LEFT JOIN Inventario i ON i.loteId = l.id " +
           "ORDER BY l.fechaVencimiento ASC")
    List<Lote> findAllLotesWithInventario();

    // HU-21: Obtener lotes bloqueados (estado = false)
    @Query("SELECT l FROM Lote l WHERE l.estado = false ORDER BY l.fechaVencimiento ASC")
    List<Lote> findLotesBloqueados();
}
