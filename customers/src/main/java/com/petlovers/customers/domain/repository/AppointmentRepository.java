package com.petlovers.customers.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petlovers.customers.domain.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
