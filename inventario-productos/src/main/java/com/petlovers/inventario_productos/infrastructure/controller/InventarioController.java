package com.petlovers.inventario_productos.infrastructure.controller;

import com.petlovers.inventario_productos.application.InventarioService;
import com.petlovers.inventario_productos.domain.model.Inventario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class InventarioController {

    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Inventario> registrar(@RequestBody Inventario inventario) {
        return ResponseEntity.ok(service.registrar(inventario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<Inventario> buscarPorProducto(@PathVariable Long idProducto) {
        return service.buscarPorProducto(idProducto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/alerta/{idProducto}")
    public ResponseEntity<Boolean> verificarAlerta(@PathVariable Long idProducto) {
        return ResponseEntity.ok(service.estaEnAlerta(idProducto));
    }

    @GetMapping
    public ResponseEntity<List<Inventario>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
