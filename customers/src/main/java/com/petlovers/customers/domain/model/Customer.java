package com.petlovers.customers.domain.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fullName", nullable = false)
	private String fullName;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "address")
	private String address;

	@Column(name = "registrationDate")
	private LocalDate registrationDate;

}
