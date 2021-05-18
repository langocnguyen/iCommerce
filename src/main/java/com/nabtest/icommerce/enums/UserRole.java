package com.nabtest.icommerce.enums;

public enum UserRole {
	ROLE_ADMIN("ROLE_ADMIN"),
	ROLE_CUSTOMER("ROLE_CUSTOMER");
	
	private String code;

	private UserRole(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}

