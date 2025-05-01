package com.petlovers.gestion_mascotas.infrastructure.persistence;

import com.petlovers.gestion_mascotas.domain.model.HistoriaClinica;
import com.petlovers.gestion_mascotas.domain.repository.HistoriaClinicaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriaClinicaJpaRepository extends JpaRepository<HistoriaClinica, Long>, HistoriaClinicaRepository {
    List<HistoriaClinica> findAllByIdMascota(Long idMascota);
}
