package com.petlovers.gestion_mascotas.application;

import com.petlovers.gestion_mascotas.domain.model.Mascota;
import com.petlovers.gestion_mascotas.domain.repository.MascotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {
    private final MascotaRepository repository;

    public MascotaService(MascotaRepository repository) {
        this.repository = repository;
    }

    public Mascota crearMascota(Mascota mascota) {
        return repository.save(mascota);
    }

    public Optional<Mascota> obtenerMascota(Long id) {
        return repository.findById(id);
    }

    public List<Mascota> listarMascotas() {
        return repository.findAll();
    }

    public void eliminarMascota(Long id) {
        repository.deleteById(id);
    }
}
