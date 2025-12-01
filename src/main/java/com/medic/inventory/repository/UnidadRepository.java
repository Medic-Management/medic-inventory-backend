package com.medic.inventory.repository;

import com.medic.inventory.entity.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnidadRepository extends JpaRepository<Unidad, Long> {
    Optional<Unidad> findByCodigo(String codigo);
}
