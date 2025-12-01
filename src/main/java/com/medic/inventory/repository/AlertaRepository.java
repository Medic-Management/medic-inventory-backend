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
}
