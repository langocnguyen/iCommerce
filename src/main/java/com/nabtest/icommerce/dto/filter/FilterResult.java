package com.nabtest.icommerce.dto.filter;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FilterResult<T> implements Serializable {

	private static final long serialVersionUID = 8190411415858688188L;

	@JsonProperty("items")
	private List<T> items;

	@JsonProperty("total")
	private long total;

	public FilterResult(List<T> items, long total) {
		super();
		this.items = items;
		this.total = total;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
}
