package com.petlovers.gestion_mascotas.application;

import com.petlovers.gestion_mascotas.domain.model.HistoriaClinica;
import com.petlovers.gestion_mascotas.domain.repository.HistoriaClinicaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoriaClinicaService {

    private final HistoriaClinicaRepository repository;

    public HistoriaClinicaService(HistoriaClinicaRepository repository) {
        this.repository = repository;
    }

    public HistoriaClinica registrar(HistoriaClinica hc) {
        return repository.save(hc);
    }

    public Optional<HistoriaClinica> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<HistoriaClinica> buscarPorMascota(Long idMascota) {
        return repository.findAllByIdMascota(idMascota);
    }
}
