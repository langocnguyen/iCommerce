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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.nabtest.icommerce.contant.Constant;
import com.nabtest.icommerce.contant.MessageConstant;

@Entity
@Table(name = "nab_product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = Constant.PRODUCT_ID)
	private Long id;

	@Column(name = Constant.PRODUCT_NAME, nullable = false)
	@NotBlank(message = MessageConstant.BLANK_PRODUCT_NAME)
	private String name;

	@Column(name = Constant.PRODUCT_PRICE, nullable = false)
	@NotNull(message = MessageConstant.NULL_PRODUCT_PRICE)
	@Min(value = 1000, message = MessageConstant.PRODUCT_PRICE_MUST_GREATER_THAN_1000)
	private Double price;
	
	@Column(name = Constant.PRODUCT_BRAND, nullable = false)
	@NotBlank(message = MessageConstant.BLANK_PRODUCT_BRAND)
	private String brand;
	
	@Column(name = Constant.PRODUCT_COLOR)
	@NotBlank(message = MessageConstant.BLANK_PRODUCT_COLOR)
	private String color;
	
	@Column(name = Constant.PRODUCT_QUANTITY)
	@Min(1)
	@NotNull(message = MessageConstant.PRODUCT_QUANTITY_VALUE)
	@Min(value = 1, message = MessageConstant.PRODUCT_QUANTITY_VALUE)
	private int quantity;
	
	@OneToMany(cascade = CascadeType.REFRESH, orphanRemoval = false, mappedBy = "product", fetch = FetchType.LAZY)
	private List<OrderDetail> orderDetails = new ArrayList<>();

	public Product() {}

	public Product(Long id, String name, Double price, String brand, int quantity) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.brand = brand;
		this.quantity = quantity;
	}

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

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
}
