package com.nabtest.icommerce.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderResponse {

	@JsonProperty
	private Long id;

	@JsonProperty
	private String fullName;

	@JsonProperty
	private String phoneNumber;

	@JsonProperty
	private String address;

	@JsonProperty
	private String email;

	@JsonProperty
	private List<OrderDetailResponse> orderDetails;
	
	@JsonProperty
	private Long totalPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<OrderDetailResponse> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetailResponse> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
}
