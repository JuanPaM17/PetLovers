package com.petlovers.gestion_mascotas.infrastructure.controller;

import com.petlovers.gestion_mascotas.application.HistoriaClinicaService;
import com.petlovers.gestion_mascotas.domain.model.HistoriaClinica;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historia-clinica")
public class HistoriaClinicaController {

    private final HistoriaClinicaService service;

    public HistoriaClinicaController(HistoriaClinicaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<HistoriaClinica> registrar(@RequestBody HistoriaClinica historia) {
        return ResponseEntity.ok(service.registrar(historia));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriaClinica> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<List<HistoriaClinica>> buscarPorMascota(@PathVariable Long idMascota) {
        return ResponseEntity.ok(service.buscarPorMascota(idMascota));
    }
}
