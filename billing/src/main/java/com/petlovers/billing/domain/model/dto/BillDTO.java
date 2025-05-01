package com.petlovers.billing.domain.model.dto;

import lombok.Data;

public class BillDTO {

	@Data
	public static class CreateBillDTO {
		private Long customerId;
		private String issuedDate;
		private String total;
		private String description;
	}

}
