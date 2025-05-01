package com.petlovers.inventario_productos.domain.repository;


import com.petlovers.inventario_productos.domain.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository {
    Producto save(Producto producto);
    Optional<Producto> findById(Long id);
    List<Producto> findAll();
    void deleteById(Long id);
}
