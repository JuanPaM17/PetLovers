package com.petlovers.gestion_mascotas.domain.repository;

import com.petlovers.gestion_mascotas.domain.model.HistoriaClinica;

import java.util.List;
import java.util.Optional;

public interface HistoriaClinicaRepository {
    HistoriaClinica save(HistoriaClinica registro);
    Optional<HistoriaClinica> findById(Long id);
    List<HistoriaClinica> findAllByIdMascota(Long idMascota);
}
