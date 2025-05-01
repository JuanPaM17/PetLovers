package com.petlovers.billing.infrastructure.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ServiceDataResponse {

	public Boolean success;
	public String statusCode;
	public String message;
	public JSONObject dataResult;
	public Integer entities;

	public ServiceDataResponse() {
	}

	public ServiceDataResponse(Boolean success, String statusCode, String message, JSONObject dataResult,
			Integer entities) {
		this.success = success;
		this.statusCode = statusCode;
		this.message = message;
		this.dataResult = dataResult;
		this.entities = entities;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getDataResponse() {
		JSONObject response = new JSONObject();
		response.put("success", success);
		response.put("statusCode", statusCode);
		response.put("message", message);
		if (dataResult != null) {
			response.put("dataResult", dataResult);
		}
		if (entities != null && !entities.equals(0)) {
			response.put("entities", entities);
		}
		return response;
	}

	public JSONObject convertObjectToJsonObject(Object objectDTO) {
		return convertObjectToJsonObject(objectDTO, null);
	}

	@SuppressWarnings("unchecked")
	public JSONObject convertObjectToJsonObject(Object objectDTO, JSONObject filterReturns) {
		JSONObject jsonObject = new JSONObject();
		JSONObject subListReturnParams = new JSONObject();
		List<String> principalListReturnParams = new ArrayList<>();
		List<String> containSubParams = new ArrayList<>();

		if (filterReturns != null && filterReturns.get("returnParamsList") != null) {
			List<String> returnParamsList = (List<String>) filterReturns.get("returnParamsList");

			for (String row : returnParamsList) {
				String[] subList = row.split("\\.");

				if (subList.length > 1) {
					String subDataReturn = row.replace(subList[0] + ".", "");
					List<String> subListReturn = new ArrayList<>();

					if (subListReturnParams != null && !subListReturnParams.isEmpty()) {
						subListReturn = (List<String>) subListReturnParams.get("returnParamsList");
						subListReturn.add(subDataReturn);
						subListReturnParams.put("returnParamsList", subListReturn);
					} else {
						subListReturn.add(subDataReturn);
						subListReturnParams.put("returnParamsList", subListReturn);
					}
					containSubParams.add(subList[0]);
				}
				principalListReturnParams.add(subList[0]);
			}
		}

		for (Field field : objectDTO.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object value = null;
			try {
				value = field.get(objectDTO);
			} catch (Exception e) {
				this.success = false;
				this.statusCode = ErrorCodes.NODATA_FOUND.getStringCode();
				this.message = "Error parsing DTO";
				return getDataResponse();
			}

			if (value != null && !(value instanceof Integer) && !(value instanceof String)
					&& !(value instanceof Boolean) && !(value instanceof BigDecimal) && !(value instanceof Long)
					&& !(value instanceof Double) && !(value instanceof JSONObject) && !(value instanceof JSONArray)) {
				try {
					List<Object> listObjectDto = new ArrayList<>();
					listObjectDto = (List<Object>) value;

					if (listObjectDto == null || listObjectDto.isEmpty()) {
						value = new JSONArray();
					} else {
						if (!(listObjectDto.get(0) instanceof Integer) && !(listObjectDto.get(0) instanceof String)
								&& !(listObjectDto.get(0) instanceof Boolean)
								&& !(listObjectDto.get(0) instanceof BigDecimal)
								&& !(listObjectDto.get(0) instanceof Long) && !(listObjectDto.get(0) instanceof Double)
								&& !(listObjectDto.get(0) instanceof JSONObject)
								&& !(listObjectDto.get(0) instanceof JSONArray)) {

							JSONArray resultArray = new JSONArray();

							for (int i = 0; i < listObjectDto.size(); i++) {
								if (containSubParams.contains(field.getName())) {
									value = convertObjectToJsonObject(listObjectDto.get(i), subListReturnParams);
								} else {
									value = convertObjectToJsonObject(listObjectDto.get(i), null);
								}
								resultArray.add(value);
							}

							value = resultArray;
						}
					}
				} catch (Exception e) {
					if (containSubParams.contains(field.getName())) {
						value = convertObjectToJsonObject(value, subListReturnParams);
					} else {
						value = convertObjectToJsonObject(value, null);
					}
				}
			}
			if (value != null) {
				if (principalListReturnParams != null && !principalListReturnParams.isEmpty()
						&& principalListReturnParams.size() > 0) {
					if (principalListReturnParams.contains(field.getName())) {
						jsonObject.put(field.getName(), value);
					}
					continue;
				}
				jsonObject.put(field.getName(), value);
			}
		}
		return jsonObject;
	}

}
