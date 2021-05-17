package com.nabtest.icommerce.service;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.nabtest.icommerce.dto.filter.FilterResult;
import com.nabtest.icommerce.dto.filter.OrderFilter;
import com.nabtest.icommerce.dto.request.OrderRequest;
import com.nabtest.icommerce.dto.response.OrderResponse;

public interface OrderService {

	public OrderResponse checkAndCreate(@Valid @NotNull OrderRequest orderRequest);

	public OrderResponse get(@NotNull @Min(1) Long id);
	
	public FilterResult<OrderResponse> get(OrderFilter filter);
}
