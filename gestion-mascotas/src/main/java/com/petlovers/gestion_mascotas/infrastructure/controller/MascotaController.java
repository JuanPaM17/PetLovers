package com.petlovers.gestion_mascotas.infrastructure.controller;

import com.petlovers.gestion_mascotas.application.MascotaService;
import com.petlovers.gestion_mascotas.domain.model.Mascota;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService service;

    public MascotaController(MascotaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Mascota> crearMascota(@RequestBody Mascota mascota) {
        return ResponseEntity.ok(service.crearMascota(mascota));}

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> obtenerMascota(@PathVariable Long id) {
        return service.obtenerMascota(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Mascota>> listar() {
        return ResponseEntity.ok(service.listarMascotas());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarMascota(id);
        return ResponseEntity.noContent().build();
    }
}
