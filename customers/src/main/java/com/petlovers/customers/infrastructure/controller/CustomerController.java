package com.petlovers.customers.infrastructure.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petlovers.customers.application.service.CustomerService;
import com.petlovers.customers.domain.dto.CustomerDTO.LoginDTO;
import com.petlovers.customers.domain.dto.CustomerDTO.createCustomerDTO;
import com.petlovers.customers.domain.model.Customer;
import com.petlovers.customers.infrastructure.config.security.JwtTokenUtil;
import com.petlovers.customers.infrastructure.utils.ErrorCodes;
import com.petlovers.customers.infrastructure.utils.ServiceDataResponse;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public ResponseEntity<JSONObject> generateJWT(@RequestBody LoginDTO loginDTO) {
		JSONObject result = new JSONObject();
		try {
			if (loginDTO == null) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(), "Body is required",
						null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}
			if (loginDTO.getEmail() == null || loginDTO.getEmail().isEmpty()) {
				result = new ServiceDataResponse(false, ErrorCodes.NODATA_FOUND.getStringCode(), "Email is required",
						null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}
			try {
				Optional<Customer> userFound = customerService.getByEmail(loginDTO.getEmail());
				if (userFound == null || userFound.isEmpty()) {
					result = new ServiceDataResponse(false, ErrorCodes.NOUSER_FOUND.getStringCode(),
							"Customer not found", null, 0).getDataResponse();
					return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
				}
				final String token = jwtTokenUtil.generateToken(userFound.get().getEmail(),
						userFound.get().getFullName());
				result = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(), token, null, 0)
						.getDataResponse();
			} catch (Exception e) {
				result = new ServiceDataResponse(false, ErrorCodes.NODATA_FOUND.getStringCode(), "Error parsing DTO",
						null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			result = new ServiceDataResponse(false, ErrorCodes.ACCESS_DENIED.getStringCode(), "Error generate token",
					null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<JSONObject> createCustomer(@RequestBody createCustomerDTO dto) {
		JSONObject result = new JSONObject();
		try {
			if (dto == null) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(), "Body is required",
						null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			if (dto.getFullName() == null || dto.getFullName().isEmpty()) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(),
						"Full name is required", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(),
						"Email is required", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			Customer customer = Customer.builder().fullName(dto.getFullName()).email(dto.getEmail())
					.phone(dto.getPhone()).address(dto.getAddress()).registrationDate(LocalDate.now()).build();

			Customer saved = customerService.createCustomer(customer);

			JSONObject data = new JSONObject();
			data.put("id", saved.getId());
			data.put("email", saved.getEmail());
			data.put("fullName", saved.getFullName());

			result = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(),
					"Customer created successfully", data, 1).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.CREATED);

		} catch (Exception e) {
			result = new ServiceDataResponse(false, ErrorCodes.GENERAL_EXCEPTION.getStringCode(),
					"Error creating customer", null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<JSONObject> getAllCustomers() {
		JSONObject result;
		try {
			List<Customer> customers = customerService.getAllCustomers();
			JSONObject data = new JSONObject();
			data.put("customers", customers);
			result = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(), "Customer list retrieved",
					data, customers.size()).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			result = new ServiceDataResponse(false, ErrorCodes.ACCESS_DENIED.getStringCode(),
					"Error retrieving customers", null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<JSONObject> getCustomerById(@PathVariable Long id) {
		try {
			return customerService.getCustomerById(id).map(customer -> {
				JSONObject data = new JSONObject();
				data.put("id", customer.getId());
				data.put("email", customer.getEmail());
				data.put("fullName", customer.getFullName());
				data.put("address", customer.getAddress());
				data.put("phone", customer.getPhone());

				JSONObject result = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(),
						"Customer retrieved successfully", data, 1).getDataResponse();

				return new ResponseEntity<>(result, HttpStatus.OK);
			}).orElseGet(() -> {
				JSONObject result = new ServiceDataResponse(false, ErrorCodes.NOUSER_FOUND.getStringCode(),
						"Customer not found", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			});
		} catch (Exception e) {
			JSONObject result = new ServiceDataResponse(false, ErrorCodes.GENERAL_EXCEPTION.getStringCode(),
					"Error retrieving customer", null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
