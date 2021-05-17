package com.nabtest.icommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(value = Include.NON_NULL)
public class ErrorResponse {

	@JsonProperty
	private String code;
	
	@JsonProperty
	private String message;
	
	@JsonProperty
	private String detailMessage;
	
	@JsonProperty
	private long time;
	
	public ErrorResponse(String code, String message, String detailMessage, long time) {
		super();
		this.code = code;
		this.message = message;
		this.detailMessage = detailMessage;
		this.time = time;
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

	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
