package com.nabtest.icommerce.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nabtest.icommerce.dto.filter.FilterResult;
import com.nabtest.icommerce.dto.filter.OrderFilter;
import com.nabtest.icommerce.dto.request.OrderRequest;
import com.nabtest.icommerce.dto.response.APIResponse;
import com.nabtest.icommerce.dto.response.OrderResponse;
import com.nabtest.icommerce.service.OrderService;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public APIResponse<FilterResult<OrderResponse>> getOrders(
			@Min(0) @RequestParam(name = "page", defaultValue = "0") int page,
			@Min(1) @RequestParam(name = "amount", defaultValue = "25", required = false) int amount) {

		OrderFilter filter = new OrderFilter(page, amount);
		FilterResult<OrderResponse> filterResult = orderService.get(filter);
		return new APIResponse<>(filterResult, System.currentTimeMillis());
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public APIResponse<OrderResponse> addOrder(@Valid @RequestBody OrderRequest orderRequest) {
		OrderResponse orderResponse = orderService.checkAndCreate(orderRequest);
		return new APIResponse<>(orderResponse, System.currentTimeMillis());
	}
	
	@GetMapping(path = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public APIResponse<OrderResponse> getOrderById(@PathVariable(value = "id") Long orderId) {
		OrderResponse orderResponseDTO = orderService.get(orderId);
		return new APIResponse<>(orderResponseDTO, System.currentTimeMillis());
	}

}
