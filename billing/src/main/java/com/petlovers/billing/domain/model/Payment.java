package com.petlovers.billing.domain.model;

import java.time.LocalDate;

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
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

	public enum PaymentMethod {
		CASH, CARD, TRANSFER
	}

	public enum PaymentStatus {
		PENDING, PAID, FAILED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "billId", nullable = false)
	private Long billId;

	@Column(name = "paymentDate", nullable = false)
	private LocalDate paymentDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "paymentMethod", length = 50)
	private PaymentMethod paymentMethod;

	@Enumerated(EnumType.STRING)
	@Column(name = "paymentStatus", length = 20)
	private PaymentStatus paymentStatus = PaymentStatus.PENDING;
}
