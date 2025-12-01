package com.medic.inventory.repository;

import com.medic.inventory.entity.UmbralStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UmbralStockRepository extends JpaRepository<UmbralStock, Long> {
    Optional<UmbralStock> findBySedeIdAndProductoId(Long sedeId, Long productoId);

    List<UmbralStock> findBySedeId(Long sedeId);

    List<UmbralStock> findByProductoId(Long productoId);
}
