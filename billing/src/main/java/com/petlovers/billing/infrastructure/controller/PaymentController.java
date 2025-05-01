package com.petlovers.billing.infrastructure.controller;

import java.time.LocalDate;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petlovers.billing.application.service.PaymentService;
import com.petlovers.billing.domain.model.Payment;
import com.petlovers.billing.domain.model.Payment.PaymentMethod;
import com.petlovers.billing.domain.model.Payment.PaymentStatus;
import com.petlovers.billing.domain.model.dto.PaymentDTO.CreatePaymentDTO;
import com.petlovers.billing.infrastructure.utils.ErrorCodes;
import com.petlovers.billing.infrastructure.utils.ServiceDataResponse;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping
	public ResponseEntity<JSONObject> createPayment(@RequestBody CreatePaymentDTO dto) {
		JSONObject result = new JSONObject();
		try {
			if (dto == null) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(), "Body is required",
						null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			if (dto.getBillId() == null) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(),
						"Bill ID is required", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			if (dto.getPaymentDate() == null || dto.getPaymentDate().isEmpty()) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(),
						"Payment date is required", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			if (dto.getPaymentMethod() == null || dto.getPaymentMethod().isEmpty()) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(),
						"Payment method is required", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			LocalDate parsedDate;
			try {
				parsedDate = LocalDate.parse(dto.getPaymentDate());
			} catch (Exception e) {
				result = new ServiceDataResponse(false, ErrorCodes.NODATA_FOUND.getStringCode(),
						"Invalid payment date format (use ISO format)", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			PaymentMethod paymentMethodEnum;
			try {
				paymentMethodEnum = PaymentMethod.valueOf(dto.getPaymentMethod().toUpperCase());
			} catch (IllegalArgumentException e) {
				result = new ServiceDataResponse(false, ErrorCodes.NODATA_FOUND.getStringCode(),
						"Invalid payment method: " + dto.getPaymentMethod(), null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			Payment payment = Payment.builder().billId(dto.getBillId()).paymentDate(parsedDate)
					.paymentMethod(paymentMethodEnum).paymentStatus(PaymentStatus.PENDING).build();

			Payment saved = paymentService.create(payment);

			JSONObject data = new JSONObject();
			data.put("id", saved.getId());
			data.put("billId", saved.getBillId());
			data.put("paymentDate", saved.getPaymentDate().toString());
			data.put("paymentMethod", saved.getPaymentMethod().name());
			data.put("paymentStatus", saved.getPaymentStatus().name());

			result = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(),
					"Payment created successfully", data, 1).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.CREATED);

		} catch (Exception e) {
			result = new ServiceDataResponse(false, ErrorCodes.GENERAL_EXCEPTION.getStringCode(),
					"Error creating payment", null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<JSONObject> getAllPayments() {
		JSONObject result;
		try {
			List<Payment> payments = paymentService.getAll();
			JSONObject data = new JSONObject();
			data.put("payments", payments);
			result = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(), "Payment list retrieved",
					data, payments.size()).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			result = new ServiceDataResponse(false, ErrorCodes.GENERAL_EXCEPTION.getStringCode(),
					"Error retrieving payments", null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<JSONObject> getPaymentById(@PathVariable Long id) {
		try {
			return paymentService.getById(id).map(payment -> {
				JSONObject data = new JSONObject();
				data.put("id", payment.getId());
				data.put("billId", payment.getBillId());
				data.put("paymentDate", payment.getPaymentDate().toString());
				data.put("paymentMethod", payment.getPaymentMethod().name());
				data.put("paymentStatus", payment.getPaymentStatus().name());

				JSONObject response = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(),
						"Payment found", data, 1).getDataResponse();

				return new ResponseEntity<>(response, HttpStatus.OK);
			}).orElseGet(() -> {
				JSONObject response = new ServiceDataResponse(false, ErrorCodes.NOUSER_FOUND.getStringCode(),
						"Payment not found", null, 0).getDataResponse();
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			});
		} catch (Exception e) {
			JSONObject response = new ServiceDataResponse(false, ErrorCodes.GENERAL_EXCEPTION.getStringCode(),
					"Error retrieving payment", null, 0).getDataResponse();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<JSONObject> deletePayment(@PathVariable Long id) {
		JSONObject result;
		try {
			paymentService.delete(id);
			result = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(),
					"Payment deleted successfully", null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			result = new ServiceDataResponse(false, ErrorCodes.GENERAL_EXCEPTION.getStringCode(),
					"Error deleting payment", null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
