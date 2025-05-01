package com.petlovers.gestion_mascotas.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "historia_clinica")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_mascota", nullable = false)
    private Long idMascota;

    @Column(nullable = false)
    private LocalDate fecha;

    private String tipoAtencion;
    private String descripcion;
    private String veterinario;
    private String observaciones;
}
