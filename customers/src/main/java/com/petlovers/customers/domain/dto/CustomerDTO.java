package com.petlovers.customers.domain.dto;

import lombok.Data;

public class CustomerDTO {

	@Data
	public static class LoginDTO {
		private String email;
	}

	@Data
	public static class createCustomerDTO {
		private String fullName;
		private String email;
		private String phone;
		private String address;
	}

}
