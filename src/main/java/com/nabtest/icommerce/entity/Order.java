package com.nabtest.icommerce.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nabtest.icommerce.contant.Constant;

@Entity
@Table(name = "nab_order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = Constant.ORDER_ID)
	private Long id;
	
	@JsonProperty(Constant.ORDER_FULL_NAME)
	@NotBlank
	@Size(min=2, max = 200, message = "Full name must have at least 2 characters and maximum 200 characters")
	@Column(name = Constant.ORDER_FULL_NAME)
	private String fullName;
	
	@NotBlank
	@Column(name = Constant.ORDER_PHONE_NUMBER)
	private String phoneNumber;

	@NotBlank
	@Column(name = Constant.ORDER_ADDRESS)
	private String address;

	@Email
	@Column(name = Constant.ORDER_EMAIL)
	private String email;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order", fetch = FetchType.EAGER)
	private List<OrderDetail> orderDetails = new ArrayList<>();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
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
