package com.medic.inventory.repository;

import com.medic.inventory.entity.Pronostico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PronosticoRepository extends JpaRepository<Pronostico, Long> {
    List<Pronostico> findBySedeId(Long sedeId);

    List<Pronostico> findByProductoId(Long productoId);

    List<Pronostico> findBySedeIdAndProductoId(Long sedeId, Long productoId);
}
