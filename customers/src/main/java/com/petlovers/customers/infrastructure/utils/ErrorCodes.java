package com.petlovers.customers.infrastructure.utils;

public enum ErrorCodes {

	// Authentication and Authorization Errors (600-699)
	ACCESS_DENIED(600, "message.label.title.accessDenied"), LOGIN_LOCK(601, ""),
	INVALID_USER(602, "message.label.title.accessDenied"),
	INVALID_PASSWORD(603, "message.label.title.incorrectPassword"), INACTIVE_USER(604, ""),
	USER_DOES_NOT_EXIST(605, "message.label.title.userNotFound"),

	// Validation and Data Errors (700-799)
	INVALID_ENTITY_TYPE(700, ""), INVALID_QUANTITY(701, ""), NODATA_FOUND(702, ""), EMPTY_PARAMETERS(703, ""),
	NOUSER_FOUND(704, "message.label.title.userNotFound"), INVALID_VALUE(705, ""), MISSING_PARAMETERS(706, ""),

	// Process and Business Errors (800-899)
	INVALID_PROCESSCODE(801, ""), ORDER_WITHOUT_INVOICE(802, ""), CHANGE_PASWORD(805, ""), SUCCESSFUL(806, ""),
	COMPLETED_WITH_ERRORS(807, ""), FALSE_FORWARD(808, ""), OUTCOME_CODES(809, ""), UPDATE_ERROR(812, ""),
	MORE_THAN_ONE(813, ""), COMPLETED_WITH_ERRORS_IN_ADDRES(814, ""), INVALID_UPDATE_ORDERS(815, ""),
	INVALID_MESSAGE(816, ""),

	// General and System Errors (900-999)
	GENERAL_EXCEPTION(900, "message.label.title.unknownError"), IO_EXCEPTION(901, ""), SAX_EXCEPTION(902, ""),
	BZFBUSINESS_EXCEPTION(903, ""), PARSE_EXCEPTION(904, ""), REST_ERROR(905, ""), UNCOMMIT_ERROR(906, ""),
	EXPEND_ERROR(907, ""), COMMIT_ERROR(908, ""), DATA_ERROR(909, ""), CANNOT_BE_DELETED(910, "");

	private Integer code;
	private String message;

	public Integer getCode() {
		return code;
	}

	public String getStringCode() {
		return code != null ? code.toString() : "";
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private ErrorCodes(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public static String getMessageByCode(String code) {
		if (code == null || code.isEmpty())
			return "";
		return getMessageByCode(Integer.parseInt(code));
	}

	public static String getMessageByCode(Integer code) {
		if (code == null || code == 0)
			return "";
		for (ErrorCodes error : ErrorCodes.values()) {
			if (error.getCode() == code) {
				return error.getMessage();
			}
		}
		return code.toString();
	}

}
