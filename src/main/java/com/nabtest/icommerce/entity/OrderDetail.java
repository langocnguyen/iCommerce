package com.nabtest.icommerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import com.nabtest.icommerce.contant.Constant;

@Entity
@Table(name = "nab_order_detail")
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = Constant.ORDER_DETAIL_ID)
	private Long id;
	
	@Column(name = Constant.PRODUCT_ID, insertable = false, updatable = false)
	private Long productId;
	
	@Column(name = Constant.ORDER_ID, insertable = false, updatable = false)
	private Long orderId;
	
	@Column(name = Constant.ORDER_DETAIL_QUANTITY)
	@Min(1)
	private int quantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = Constant.ORDER_ID)
    private Order order;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = Constant.PRODUCT_ID)
    private Product product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
