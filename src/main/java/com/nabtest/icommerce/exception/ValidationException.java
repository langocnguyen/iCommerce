package com.nabtest.icommerce.exception;

import com.nabtest.icommerce.enums.ErrorType;

public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private ErrorType error;

	public ValidationException(String message) {
		super(message);
	}
	
	public ValidationException(ErrorType error) {
		super(error.getMessage());
		this.error = error;
	}

	public ErrorType getError() {
		return error;
	}

	public void setError(ErrorType error) {
		this.error = error;
	}
	
}
