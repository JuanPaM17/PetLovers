package com.petlovers.customers.domain.dto;

import lombok.Data;

public class AppointmentDTO {

	@Data
	public static class CreateAppointmentDTO {
		private Long customerId;
		private Long petId;
		private String dateTime;
		private String serviceType;
	}

}
