package com.nabtest.icommerce.dto.filter;

import javax.validation.constraints.Min;

public class ProductFilter extends FilterDTO {

	private String name;

	@Min(1)
	private Double price;

	private String brand;

	private String color;
	
	@Min(1)
	private Double priceFrom;
	
	@Min(1)
	private Double priceTo;

	public ProductFilter(int page, int amount) {
		super(page, amount);
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

	public Double getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(Double priceFrom) {
		this.priceFrom = priceFrom;
	}

	public Double getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(Double priceTo) {
		this.priceTo = priceTo;
	}
	
}
