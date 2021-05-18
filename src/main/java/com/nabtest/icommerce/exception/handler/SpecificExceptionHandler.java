package com.nabtest.icommerce.exception.handler;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.nabtest.icommerce.dto.response.ErrorResponse;
import com.nabtest.icommerce.enums.ErrorType;
import com.nabtest.icommerce.exception.ValidationException;

@Slf4j
@RestControllerAdvice
public class SpecificExceptionHandler {
	
	private static Logger logger = LoggerFactory.getLogger(SpecificExceptionHandler.class);

	@ExceptionHandler({ConstraintViolationException.class, MethodArgumentTypeMismatchException.class,
		InvalidFormatException.class, MissingServletRequestParameterException.class, MethodArgumentNotValidException.class})
	public ResponseEntity<ErrorResponse> handleValidationException(Exception ex, WebRequest request){
		long time = System.currentTimeMillis();
		ErrorType error = ErrorType.ERROR_REQUEST_VALIDATION_FAIL;
		String detailMessage = null;
		List<String> messages = null;
		if(ex instanceof MethodArgumentNotValidException) {
			List<FieldError> fieldErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors();
			messages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
			detailMessage = StringUtils.join(messages, ", ");
		}
		
		if(ex instanceof ConstraintViolationException) {
			Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) ex).getConstraintViolations();
			messages = constraintViolations.stream().map(constraint -> {
				String propertyPath = constraint.getPropertyPath().toString();
				propertyPath = propertyPath.substring(propertyPath.lastIndexOf(".") + 1);
				return propertyPath.concat(": ").concat(constraint.getMessage());
			}).collect(Collectors.toList());
			detailMessage = StringUtils.join(messages, ", ");
		}
		
		ErrorResponse errorResponse = new ErrorResponse(error.getCode(), error.getMessage(), detailMessage, time);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ValidationException.class})
	public ResponseEntity<ErrorResponse> handleLegacyException(ValidationException ex, WebRequest request){
		long time = System.currentTimeMillis();
		ErrorType error = ex.getError();
		String detailMessage = null;
		ErrorResponse errorResponse = new ErrorResponse(error.getCode(), error.getMessage(), detailMessage, time);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> handleOverallException(Exception ex, WebRequest request) {
		logger.error("Internal server error", ex);
		long time = System.currentTimeMillis();
		ErrorType error = ErrorType.ERROR_INTERNAL_SERVER;
		String detailMessage = null;
		ErrorResponse errorResponse = new ErrorResponse(error.getCode(), error.getMessage(), detailMessage, time);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
