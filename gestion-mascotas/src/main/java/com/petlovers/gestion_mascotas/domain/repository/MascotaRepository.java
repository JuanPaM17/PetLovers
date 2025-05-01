package com.petlovers.gestion_mascotas.domain.repository;

import com.petlovers.gestion_mascotas.domain.model.Mascota;

import java.util.List;
import java.util.Optional;

public interface MascotaRepository {
    Mascota save(Mascota mascota);
    Optional<Mascota> findById(Long id);
    List<Mascota> findAll();
    void deleteById(Long id);
}
