package com.nabtest.icommerce.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class APIResponse<T> implements Serializable {

	private static final long serialVersionUID = 1973170802700506396L;

	@JsonProperty("data")
	private T data;
	
	@JsonProperty("time")
	private long time;

	public APIResponse(T data, long time) {
		this.data = data;
		this.time = time;
	}
}
