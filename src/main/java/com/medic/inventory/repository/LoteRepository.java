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

    @Query("SELECT l FROM Lote l WHERE l.fechaVencimiento <= :fecha AND l.estado = true")
    List<Lote> findLotesProximosAVencer(LocalDate fecha);

    // HU-11: FEFO - First Expired, First Out
    @Query("SELECT l FROM Lote l WHERE l.producto.id = :productoId AND l.estado = true ORDER BY l.fechaVencimiento ASC")
    List<Lote> findByProductoIdOrderByFechaVencimientoAsc(Long productoId);

    // HU-11: FEFO con filtro por sede
    @Query("SELECT DISTINCT l FROM Lote l " +
           "JOIN Inventario i ON i.lote.id = l.id " +
           "WHERE l.producto.id = :productoId " +
           "AND i.sede.id = :sedeId " +
           "AND i.cantidadDisponible > 0 " +
           "AND l.estado = true " +
           "ORDER BY l.fechaVencimiento ASC")
    List<Lote> findByProductoIdAndSedeIdWithStockOrderByFechaVencimiento(Long productoId, Long sedeId);
}
