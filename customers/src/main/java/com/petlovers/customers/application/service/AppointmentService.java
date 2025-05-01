package com.petlovers.customers.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petlovers.customers.domain.model.Appointment;
import com.petlovers.customers.domain.repository.AppointmentRepository;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	public Appointment createAppointment(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}

	public List<Appointment> getAllAppointments() {
		return appointmentRepository.findAll();
	}

	public Optional<Appointment> getAppointmentById(Long id) {
		return appointmentRepository.findById(id);
	}

	public Appointment updateAppointment(Long id, Appointment updated) {
		return appointmentRepository.findById(id).map(existing -> {
			existing.setDateTime(updated.getDateTime());
			existing.setServiceType(updated.getServiceType());
			existing.setStatus(updated.getStatus());
			return appointmentRepository.save(existing);
		}).orElseThrow(() -> new RuntimeException("Appointment not found"));
	}

	public void deleteAppointment(Long id) {
		appointmentRepository.deleteById(id);
	}

}
