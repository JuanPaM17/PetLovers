package com.petlovers.customers.infrastructure.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petlovers.customers.application.service.AppointmentService;
import com.petlovers.customers.domain.dto.AppointmentDTO.CreateAppointmentDTO;
import com.petlovers.customers.domain.model.Appointment;
import com.petlovers.customers.infrastructure.utils.ErrorCodes;
import com.petlovers.customers.infrastructure.utils.ServiceDataResponse;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@PostMapping
	public ResponseEntity<JSONObject> createAppointment(@RequestBody CreateAppointmentDTO dto) {
		JSONObject result = new JSONObject();
		try {
			if (dto == null) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(), "Body is required",
						null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

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

			if (dto.getPetId() == null) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(),
						"Pet ID is required", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			if (dto.getDateTime() == null || dto.getDateTime().isEmpty()) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(),
						"DateTime is required", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			if (dto.getServiceType() == null || dto.getServiceType().isEmpty()) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(),
						"Service type is required", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			Appointment.ServiceType serviceTypeEnum;
			try {
				serviceTypeEnum = Appointment.ServiceType.valueOf(dto.getServiceType().toUpperCase());
			} catch (IllegalArgumentException e) {
				result = new ServiceDataResponse(false, ErrorCodes.NODATA_FOUND.getStringCode(),
						"Invalid service type: " + dto.getServiceType(), null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			LocalDateTime dateTimeParsed;
			try {
				dateTimeParsed = LocalDateTime.parse(dto.getDateTime());
			} catch (Exception e) {
				result = new ServiceDataResponse(false, ErrorCodes.NODATA_FOUND.getStringCode(),
						"Invalid date format (use ISO format)", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			Appointment appointment = Appointment.builder().customerId(dto.getCustomerId()).petId(dto.getPetId())
					.dateTime(dateTimeParsed).serviceType(serviceTypeEnum).status(Appointment.AppointmentStatus.PENDING)
					.build();

			Appointment saved = appointmentService.createAppointment(appointment);

			JSONObject data = new JSONObject();
			data.put("id", saved.getId());
			data.put("serviceType", saved.getServiceType().name());
			data.put("dateTime", saved.getDateTime().toString());
			data.put("status", saved.getStatus().name());

			result = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(),
					"Appointment created successfully", data, 1).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.CREATED);

		} catch (Exception e) {
			result = new ServiceDataResponse(false, ErrorCodes.GENERAL_EXCEPTION.getStringCode(),
					"Error creating appointment", null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<JSONObject> getAllAppointments() {
		JSONObject result;
		try {
			List<Appointment> appointments = appointmentService.getAllAppointments();
			JSONObject data = new JSONObject();
			data.put("appointments", appointments);
			result = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(), "Appointment list retrieved",
					data, appointments.size()).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			result = new ServiceDataResponse(false, ErrorCodes.GENERAL_EXCEPTION.getStringCode(),
					"Error retrieving appointments", null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<JSONObject> getAppointmentById(@PathVariable Long id) {
		JSONObject response = new JSONObject();
		try {
			Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
			if (appointment.isPresent()) {
				JSONObject data = new JSONObject();
				data.put("id", appointment.get().getId());
				data.put("customerId", appointment.get().getCustomerId());
				data.put("petId", appointment.get().getPetId());
//				data.put("dateTime", appointment.getDateTime().toString());
//				data.put("serviceType", appointment.getServiceType().name());
//				data.put("status", appointment.getStatus().name());
				response = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(),
						"Appointment found", data, 1).getDataResponse();
			} else {
				response = new ServiceDataResponse(false, ErrorCodes.NOUSER_FOUND.getStringCode(),
						"Appointment not found", null, 0).getDataResponse();
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response = new ServiceDataResponse(false, ErrorCodes.GENERAL_EXCEPTION.getStringCode(),
					"Error retrieving appointment", null, 0).getDataResponse();
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<JSONObject> updateAppointment(@PathVariable Long id, @RequestBody CreateAppointmentDTO dto) {
		JSONObject result;
		try {
			if (dto == null) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(), "Body is required",
						null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			if (dto.getDateTime() == null || dto.getDateTime().isEmpty()) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(),
						"DateTime is required", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			if (dto.getServiceType() == null || dto.getServiceType().isEmpty()) {
				result = new ServiceDataResponse(false, ErrorCodes.EMPTY_PARAMETERS.getStringCode(),
						"Service type is required", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			LocalDateTime dateTimeParsed;
			try {
				dateTimeParsed = LocalDateTime.parse(dto.getDateTime());
			} catch (Exception e) {
				result = new ServiceDataResponse(false, ErrorCodes.NODATA_FOUND.getStringCode(),
						"Invalid date format (use ISO format)", null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}

			Appointment.ServiceType serviceTypeEnum;
			try {
				serviceTypeEnum = Appointment.ServiceType.valueOf(dto.getServiceType().toUpperCase());
			} catch (IllegalArgumentException e) {
				result = new ServiceDataResponse(false, ErrorCodes.NODATA_FOUND.getStringCode(),
						"Invalid service type: " + dto.getServiceType(), null, 0).getDataResponse();
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}
			System.out.println(id);
			Appointment updated = appointmentService.updateAppointment(id,
					Appointment.builder().dateTime(dateTimeParsed).serviceType(serviceTypeEnum).build());

			JSONObject data = new JSONObject();
			data.put("id", updated.getId());
			data.put("dateTime", updated.getDateTime().toString());
			data.put("serviceType", updated.getServiceType().name());
//			data.put("status", updated.getStatus().name());

			result = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(),
					"Appointment updated successfully", data, 1).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (RuntimeException e) {
			result = new ServiceDataResponse(false, ErrorCodes.NOUSER_FOUND.getStringCode(), "Appointment not found",
					null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			result = new ServiceDataResponse(false, ErrorCodes.GENERAL_EXCEPTION.getStringCode(),
					"Error updating appointment", null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<JSONObject> deleteAppointment(@PathVariable Long id) {
		JSONObject result;
		try {
			appointmentService.deleteAppointment(id);
			result = new ServiceDataResponse(true, ErrorCodes.SUCCESSFUL.getStringCode(),
					"Appointment deleted successfully", null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			result = new ServiceDataResponse(false, ErrorCodes.GENERAL_EXCEPTION.getStringCode(),
					"Error deleting appointment", null, 0).getDataResponse();
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
