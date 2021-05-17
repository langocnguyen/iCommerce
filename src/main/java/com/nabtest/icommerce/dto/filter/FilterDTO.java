package com.nabtest.icommerce.dto.filter;

import javax.validation.constraints.Min;

public class FilterDTO {
	
	@Min(0)
	protected int page;
	
	@Min(1)
	protected int amount;
	
	protected String orderBy;
	
	protected String order;
	
	public FilterDTO(int page, int amount) {
		this.page = page;
		this.amount = amount;
	}

	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
}
