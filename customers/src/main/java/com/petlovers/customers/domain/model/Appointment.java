package com.petlovers.customers.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "appointment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

	public enum ServiceType {
		VACCINATION, GROOMING, CONSULTATION
	}

	public enum AppointmentStatus {
		PENDING, COMPLETED, CANCELED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "customerId", nullable = false)
	private Long customerId;

	@Column(name = "petId", nullable = false)
	private Long petId;

	@Column(name = "dateTime", nullable = false)
	private LocalDateTime dateTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "serviceType", length = 100)
	private ServiceType serviceType;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", length = 20)
	private AppointmentStatus status = AppointmentStatus.PENDING;
}
