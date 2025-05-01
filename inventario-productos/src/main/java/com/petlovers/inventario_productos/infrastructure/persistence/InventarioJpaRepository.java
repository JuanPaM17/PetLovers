package com.petlovers.inventario_productos.infrastructure.persistence;

import com.petlovers.inventario_productos.domain.model.Inventario;
import com.petlovers.inventario_productos.domain.repository.InventarioRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventarioJpaRepository extends JpaRepository<Inventario, Long>, InventarioRepository {
    Optional<Inventario> findByIdProducto(Long idProducto);
}
