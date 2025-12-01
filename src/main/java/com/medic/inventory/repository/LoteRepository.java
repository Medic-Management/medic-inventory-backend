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
}
