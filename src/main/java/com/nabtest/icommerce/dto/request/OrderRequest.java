package com.nabtest.icommerce.dto.request;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nabtest.icommerce.contant.Constant;
import com.nabtest.icommerce.contant.MessageConstant;;

public class OrderRequest {

	@JsonProperty(Constant.ORDER_FULL_NAME)
	@NotBlank(message = MessageConstant.BLANK_FULL_NAME)
	@Size(min = 2, max = 200, message = MessageConstant.FULL_NAME_LENGTH)
	private String fullName;

	@JsonProperty(Constant.ORDER_PHONE_NUMBER)
	@NotBlank(message = MessageConstant.BLANK_PHONE_NUMBER)
	private String phoneNumber;

	@JsonProperty(Constant.ORDER_ADDRESS)
	@NotBlank(message = MessageConstant.BLANK_ADDRESS)
	private String address;

	@JsonProperty(Constant.ORDER_EMAIL)
	@Email(message = MessageConstant.INVALID_EMAIL)
	@NotBlank(message = MessageConstant.BLANK_EMAIL)
	private String email;

	@JsonProperty(Constant.ORDER_DETAILS)
	@NotEmpty
	List<com.nabtest.icommerce.dto.request.OrderDetailRequest> orderDetails;

	public List<OrderDetailRequest> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetailRequest> orderDetails) {
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
}
