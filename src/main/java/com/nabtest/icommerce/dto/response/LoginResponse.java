package com.nabtest.icommerce.dto.response;

public class LoginResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	
	public LoginResponse(String accessToken) {
		this.setAccessToken(accessToken);
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getTokenType() {
		return tokenType;
	}
	
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
}
