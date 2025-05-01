package com.petlovers.inventario_productos.domain.repository;

import com.petlovers.inventario_productos.domain.model.Inventario;

import java.util.List;
import java.util.Optional;

public interface InventarioRepository {
    Inventario save(Inventario inventario);
    Optional<Inventario> findById(Long id);
    Optional<Inventario> findByIdProducto(Long idProducto);
    List<Inventario> findAll();
    void deleteById(Long id);
}
