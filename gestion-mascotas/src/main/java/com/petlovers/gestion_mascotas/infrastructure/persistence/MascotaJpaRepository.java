package com.petlovers.gestion_mascotas.infrastructure.persistence;

import com.petlovers.gestion_mascotas.domain.model.Mascota;
import com.petlovers.gestion_mascotas.domain.repository.MascotaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MascotaJpaRepository extends JpaRepository<Mascota, Long>, MascotaRepository {
    // Los métodos de JpaRepository ya cumplen con la interfaz
}