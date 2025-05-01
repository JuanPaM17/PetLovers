package com.petlovers.billing.domain.model.dto;

import lombok.Data;

public class PaymentDTO {

	@Data
	public static class CreatePaymentDTO {
		private Long billId;
		private String paymentDate;
		private String paymentMethod;
	}

}
