package com.petlovers.billing.domain.model;

import java.math.BigDecimal;
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
@Table(name = "bill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "customerId", nullable = false)
	private Long customerId;

	@Column(name = "issuedDate", nullable = false)
	private LocalDate issuedDate;

	@Column(name = "total", nullable = false)
	private BigDecimal total;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

}
