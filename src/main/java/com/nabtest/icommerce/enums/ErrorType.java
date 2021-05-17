package com.nabtest.icommerce.enums;

public enum ErrorType {
	ERROR_PRODUCT_NAME_EXISTS("S0000001", "Product name has already exists"),
	ERROR_PRODUCT_PRICE_NEGATIVE("S0000002", "Product price must not be negative"),
	ERROR_PRODUCT_NOT_FOUND("S0000003", "Product not found"),
	ERROR_REQUEST_VALIDATION_FAIL("S0000004", "Request validation fail"),
	ERROR_INTERNAL_SERVER("S0000005", "Internal Server Error"),
	ERROR_REQUEST_ORDER_PARAM_IS_INVALID("S0000006", "Request parameter is invalid"),
	ERROR_REQUEST_ORDER_BY_PARAM_IS_INVALID("S0000007", "Request parameter is invalid"),
	ERROR_REMAINING_IS_LESS_THAN_NUMBER_OF_PRODUCT_IN_REQUEST("S0000008", "Remaining quanity of product is less than the number of product from request"),
	ERROR_INVALID_PHONE_NUMBER("S0000009", "Invalid phone number");

	private String code;
	private String message;

	ErrorType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
