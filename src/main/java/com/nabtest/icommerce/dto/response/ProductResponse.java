package com.nabtest.icommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductResponse {

	@JsonProperty
	private Long id;
	
	@JsonProperty
	private String name;

	@JsonProperty
	private Double price;
	
	@JsonProperty
	private String brand;
	
	@JsonProperty
	private String color; 
	
	@JsonProperty
	private int quantity; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
