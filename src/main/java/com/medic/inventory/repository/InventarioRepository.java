package com.medic.inventory.repository;

import com.medic.inventory.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    List<Inventario> findBySedeId(Long sedeId);

    List<Inventario> findByLoteId(Long loteId);

    java.util.Optional<Inventario> findBySedeIdAndLoteId(Long sedeId, Long loteId);
}
