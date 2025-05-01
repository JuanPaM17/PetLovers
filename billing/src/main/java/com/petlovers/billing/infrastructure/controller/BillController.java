package com.petlovers.billing.infrastructure.controller;

import java.math.BigDecimal;
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

import com.petlovers.billing.application.service.BillService;
import com.petlovers.billing.domain.model.Bill;
import com.petlovers.billing.domain.model.dto.BillDTO.CreateBillDTO;
import com.petlovers.billing.infrastructure.utils.ErrorCodes;
import com.petlovers.billing.infrastructure.utils.ServiceDataResponse;

@RestController
@RequestMapping("/bills")
public class BillController {

	@Autowired
	private BillService billService;

	@PostMapping
	public ResponseEntity<JSONObject> createBill(@RequestBody CreateBillDTO dto) {
		JSONObject result = new JSONObject();
		try {
			if (dto == null) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(), "Body is required",
						null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			if (dto.getCustomerId() == null) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(),
						"Customer ID is required", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			if (dto.getIssuedDate() == null || dto.getIssuedDate().isEmpty()) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(),
						"Issued date is required", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			if (dto.getTotal() == null || dto.getTotal().isEmpty()) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(),
						"Total is required", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			LocalDate parsedDate;
			try {
				parsedDate = LocalDate.parse(dto.getIssuedDate());
			} catch (Exception e) {
				result = new ServiceDataResponse(false, ErrorCodes.NODATA_FOUND.getStringCode(),
						"Invalid date format (use ISO format)", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			BigDecimal parsedTotal;
			try {
				parsedTotal = new BigDecimal(dto.getTotal());
			} catch (Exception e) {
				result = new ServiceDataResponse(false, ErrorCodes.NODATA_FOUND.getStringCode(), "Invalid total format",
						null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			Bill bill = Bill.builder().customerId(dto.getCustomerId()).issuedDate(parsedDate).total(parsedTotal)
					.description(dto.getDescription()).build();

			Bill saved = billService.create(bill);

			JSONObject data = new JSONObject();
			data.put("id", saved.getId());
			data.put("customerId", saved.getCustomerId());
			data.put("issuedDate", saved.getIssuedDate().toString());
			data.put("total", saved.getTotal().toString());
			data.put("description", saved.getDescription());
			result = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(), "Bill created successfully",
					data, 1).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.CREATED);

		} catch (Exception e) {
			result = new ServiceDataResponse(false, ErrorCodes.GENERAL_EXCEPTION.getStringCode(), "Error creating bill",
					null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<JSONObject> getAllBills() {
		JSONObject result;
		try {
			List<Bill> bills = billService.getAll();
			JSONObject data = new JSONObject();
			data.put("bills", bills);
			result = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(), "Bill list retrieved", data,
					bills.size()).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			result = new ServiceDataResponse(false, ErrorCodes.GENERAL_EXCEPTION.getStringCode(),
					"Error retrieving bills", null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<JSONObject> getBillById(@PathVariable Long id) {
		try {
			return billService.getById(id).map(bill -> {
				JSONObject data = new JSONObject();
				data.put("id", bill.getId());
				data.put("customerId", bill.getCustomerId());
				data.put("issuedDate", bill.getIssuedDate().toString());
				data.put("total", bill.getTotal().toString());
				data.put("description", bill.getDescription());

				JSONObject response = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(), "Bill found",
						data, 1).getDataResponse();

				return new ResponseEntity<>(response, HttpStatus.OK);
			}).orElseGet(() -> {
				JSONObject response = new ServiceDataResponse(false, ErrorCodes.NOUSER_FOUND.getStringCode(),
						"Bill not found", null, 0).getDataResponse();
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			});
		} catch (Exception e) {
			JSONObject response = new ServiceDataResponse(false, ErrorCodes.GENERAL_EXCEPTION.getStringCode(),
					"Error retrieving bill", null, 0).getDataResponse();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<JSONObject> deleteBill(@PathVariable Long id) {
		JSONObject result;
		try {
			billService.delete(id);
			result = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(), "Bill deleted successfully",
					null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			result = new ServiceDataResponse(false, ErrorCodes.GENERAL_EXCEPTION.getStringCode(), "Error deleting bill",
					null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
