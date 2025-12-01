package com.medic.inventory.repository;

import com.medic.inventory.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findBySedeIdOrderByOcurrioEnDesc(Long sedeId);

    List<Movimiento> findByLoteIdOrderByOcurrioEnDesc(Long loteId);

    List<Movimiento> findByCreadoPorAndTipo(Integer creadoPor, String tipo);

    @Query("SELECT m FROM Movimiento m WHERE m.ocurrioEn BETWEEN :inicio AND :fin ORDER BY m.ocurrioEn DESC")
    List<Movimiento> findMovimientosPorPeriodo(LocalDateTime inicio, LocalDateTime fin);
}
