package com.medic.inventory.repository;

import com.medic.inventory.entity.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    @Query("SELECT a FROM Alerta a WHERE a.activa = 1 ORDER BY a.disparadaEn DESC")
    List<Alerta> findAlertasActivas();

    List<Alerta> findBySedeIdAndActiva(Long sedeId, Integer activa);

    List<Alerta> findByProductoIdAndActiva(Long productoId, Integer activa);

    List<Alerta> findByActivaOrderByDisparadaEnDesc(Integer activa);

    boolean existsBySedeIdAndProductoIdAndActiva(Long sedeId, Long productoId, Integer activa);

    List<Alerta> findBySedeIdAndProductoIdAndActiva(Long sedeId, Long productoId, Integer activa);

    // HU-17: Métodos para alertas de cobertura
    boolean existsBySedeIdAndProductoIdAndTipoAndActiva(Long sedeId, Long productoId, String tipo, Integer activa);

    List<Alerta> findBySedeIdAndProductoIdAndTipoAndActiva(Long sedeId, Long productoId, String tipo, Integer activa);

    List<Alerta> findByTipoAndActivaOrderByNivelDesc(String tipo, Integer activa);

    // Alertas activas de un producto excluyendo un tipo (para no pisar COBERTURA_BAJA
    // desde la verificación de stock, que opera sobre umbral mínimo y no sobre cobertura)
    @Query("SELECT (COUNT(a) > 0) FROM Alerta a WHERE a.sede.id = :sedeId AND a.producto.id = :productoId AND a.activa = :activa AND a.tipo <> :tipoExcluido")
    boolean existsBySedeIdAndProductoIdAndActivaAndTipoNot(Long sedeId, Long productoId, Integer activa, String tipoExcluido);

    @Query("SELECT a FROM Alerta a WHERE a.sede.id = :sedeId AND a.producto.id = :productoId AND a.activa = :activa AND a.tipo <> :tipoExcluido")
    List<Alerta> findBySedeIdAndProductoIdAndActivaAndTipoNot(Long sedeId, Long productoId, Integer activa, String tipoExcluido);

    // HU-03 Escenario 2: Reporte consolidado de alertas por rango de fechas
    List<Alerta> findByDisparadaEnBetween(java.time.LocalDateTime desde, java.time.LocalDateTime hasta);
}
