package com.petlovers.inventario_productos.application;

import com.petlovers.inventario_productos.domain.model.Producto;
import com.petlovers.inventario_productos.domain.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public Producto registrar(Producto producto) {
        return repository.save(producto);
    }

    public Optional<Producto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Producto> listar() {
        return repository.findAll();
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
