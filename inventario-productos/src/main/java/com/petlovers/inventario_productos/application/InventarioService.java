package com.petlovers.inventario_productos.application;

import com.petlovers.inventario_productos.domain.model.Inventario;
import com.petlovers.inventario_productos.domain.repository.InventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    private final InventarioRepository repository;

    public InventarioService(InventarioRepository repository) {
        this.repository = repository;
    }

    public Inventario registrar(Inventario inventario) {
        return repository.save(inventario);
    }

    public Optional<Inventario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Inventario> buscarPorProducto(Long idProducto) {
        return repository.findByIdProducto(idProducto);
    }

    public List<Inventario> listar() {
        return repository.findAll();
    }

    public boolean estaEnAlerta(Long idProducto) {
        return repository.findByIdProducto(idProducto)
                .map(inv -> inv.getCantidad() <= inv.getUmbralAlerta())
                .orElse(false);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
