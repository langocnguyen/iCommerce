package com.nabtest.icommerce.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nabtest.icommerce.contant.Constant;
import com.nabtest.icommerce.contant.MessageConstant;

public class OrderDetailRequest {
	
	@JsonProperty(Constant.PRODUCT_ID)
	@NotNull(message = MessageConstant.NULL_PRODUCT_ID)
	private Long productId;
	
	@JsonProperty(Constant.ORDER_DETAIL_QUANTITY)
	@NotNull(message = MessageConstant.NULL_ORDER_DETAIL_QUANTITY)
	@Min(value = 1, message = MessageConstant.ORDER_DETAIL_QUANTITY_GREATER_THAN_ONE)
	private Integer quantity;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
