package com.nabtest.icommerce.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nabtest.icommerce.contant.Constant;
import com.nabtest.icommerce.contant.MessageConstant;

public class ProductRequest {

	@JsonProperty(Constant.PRODUCT_NAME)
	@NotBlank(message = MessageConstant.BLANK_PRODUCT_NAME)
	private String name;

	@JsonProperty(Constant.PRODUCT_PRICE)
	@NotNull(message = MessageConstant.NULL_PRODUCT_PRICE)
	@Min(value = 1000, message = MessageConstant.PRODUCT_PRICE_MUST_GREATER_THAN_1000)
	private Double price;
	
	@JsonProperty(Constant.PRODUCT_BRAND)
	@NotNull(message = MessageConstant.BLANK_PRODUCT_BRAND)
	private String brand;
	
	@JsonProperty(Constant.PRODUCT_COLOR)
	@NotBlank(message = MessageConstant.BLANK_PRODUCT_COLOR)
	private String color;
	
	@JsonProperty(Constant.PRODUCT_QUANTITY)
	@NotNull(message = MessageConstant.PRODUCT_QUANTITY_VALUE)
	@Min(value = 1, message = MessageConstant.PRODUCT_QUANTITY_VALUE)
	private Integer quantity;

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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
