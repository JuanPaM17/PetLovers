package com.petlovers.inventario_productos.infrastructure.persistence;

import com.petlovers.inventario_productos.domain.model.Producto;
import com.petlovers.inventario_productos.domain.repository.ProductoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoJpaRepository extends JpaRepository<Producto, Long>, ProductoRepository {
}
