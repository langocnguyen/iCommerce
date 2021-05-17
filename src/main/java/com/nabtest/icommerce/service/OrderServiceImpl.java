package com.nabtest.icommerce.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nabtest.icommerce.dto.filter.FilterResult;
import com.nabtest.icommerce.dto.filter.OrderFilter;
import com.nabtest.icommerce.dto.request.OrderDetailRequest;
import com.nabtest.icommerce.dto.request.OrderRequest;
import com.nabtest.icommerce.dto.response.OrderDetailResponse;
import com.nabtest.icommerce.dto.response.OrderResponse;
import com.nabtest.icommerce.dto.response.ProductResponse;
import com.nabtest.icommerce.entity.Order;
import com.nabtest.icommerce.entity.OrderDetail;
import com.nabtest.icommerce.entity.Product;
import com.nabtest.icommerce.enums.ErrorType;
import com.nabtest.icommerce.exception.ValidationException;
import com.nabtest.icommerce.repository.OrderRepository;
import com.nabtest.icommerce.utils.Utils;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductService productService;
	
	@Override
	public OrderResponse checkAndCreate(OrderRequest orderRequest) {
		// Check phone number
		String phoneNumber = orderRequest.getPhoneNumber();
		checkPhoneNumber(phoneNumber);
		
		// Handle duplicate product (Multiple order detail have the same product)
		List<OrderDetailRequest> orderDetailRequests = orderRequest.getOrderDetails();
		Map<Long, OrderDetailRequest> productOrderDetailMap = new LinkedHashMap<>();
		collectDuplicateProducts(orderDetailRequests, productOrderDetailMap);

		// Create order after handling duplicate product base on the map
		Order order = createOrder(orderRequest, productOrderDetailMap);

		return transformToResponse(order);
	}

	@Override
	public OrderResponse get(Long id) {
		Optional<Order> optionalOrder = orderRepository.findById(id);
		if (optionalOrder.isPresent()) {
			return transformToResponse(optionalOrder.get());
		}

		throw new ValidationException(ErrorType.ERROR_PRODUCT_NOT_FOUND);
	}

	@Override
	public FilterResult<OrderResponse> get(OrderFilter filter) {
		PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getAmount());
		Page<Order> page = orderRepository.findAll(pageRequest);
		List<Order> products = page.getContent();
		List<OrderResponse> productResponses = new ArrayList<>();
		if (!CollectionUtils.isEmpty(products)) {
			products.forEach(order -> productResponses.add(transformToResponse(order)));
		}
		return new FilterResult<>(productResponses, page.getTotalElements());
	}
	
	private OrderResponse transformToResponse(Order orderEntity) {
		OrderResponse orderResponse = new OrderResponse();
		BeanUtils.copyProperties(orderEntity, orderResponse);
		List<OrderDetail> orderDetails = orderEntity.getOrderDetails();
		List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
		Double totalPrice = Double.valueOf(0);
		for(OrderDetail orderDetail : orderDetails) {
			OrderDetailResponse orderDetailResponse = transformToResponse(orderDetail);
			orderDetailResponses.add(orderDetailResponse);
			// calculate total price of order
			totalPrice += (orderDetailResponse.getQuantity() * orderDetailResponse.getProduct().getPrice());
		}
		orderResponse.setOrderDetails(orderDetailResponses);
		orderResponse.setTotalPrice(totalPrice.longValue());
		return orderResponse;
	}
	
	private OrderDetailResponse transformToResponse(OrderDetail orderDetailEntity) {
		OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
		BeanUtils.copyProperties(orderDetailEntity, orderDetailResponse);
		Product product = orderDetailEntity.getProduct();
		ProductResponse productResponse = productService.transformToResponse(product);
		orderDetailResponse.setProduct(productResponse);
		return orderDetailResponse;
	}

	private Order createOrder(OrderRequest orderRequest, Map<Long, OrderDetailRequest> productOrderDetailMap) {
		List<OrderDetail> orderDetails = new ArrayList<>();
		Order order = new Order();
		BeanUtils.copyProperties(orderRequest, order);
		for (Map.Entry<Long, OrderDetailRequest> entry : productOrderDetailMap.entrySet()) {
			OrderDetail orderDetail = new OrderDetail();
			Long productId = entry.getKey();
			Product product = productService.getEntity(productId);
			if (product == null) {
				throw new ValidationException(ErrorType.ERROR_PRODUCT_NOT_FOUND);
			}

			// Checking remaining quantity of product
			int sellingQuanity = productService.countNumberOfSellingProduct(productId);
			int remainingQuantity = product.getQuantity() - sellingQuanity;
			OrderDetailRequest orderDetailRequest = entry.getValue();
			if (orderDetailRequest.getQuantity() > remainingQuantity) {
				throw new ValidationException(ErrorType.ERROR_REMAINING_IS_LESS_THAN_NUMBER_OF_PRODUCT_IN_REQUEST);
			}

			orderDetail.setProduct(product);
			orderDetail.setQuantity(orderDetailRequest.getQuantity());
			orderDetail.setOrder(order);
			orderDetails.add(orderDetail);
		}

		order.setOrderDetails(orderDetails);
		return orderRepository.save(order);
	}

	private void checkPhoneNumber(String phoneNumber) {
		boolean isValidVietNamPhoneNumber = Utils.isValidVietNamPhoneNumber(phoneNumber);
		if(!isValidVietNamPhoneNumber) {
			throw new ValidationException(ErrorType.ERROR_INVALID_PHONE_NUMBER);
		}
	}

	private void collectDuplicateProducts(List<OrderDetailRequest> orderDetailRequests,
			Map<Long, OrderDetailRequest> productOrderDetailMap) {
		// Collect quantity of order detail which has the same product
		for (OrderDetailRequest orderDetailRequest : orderDetailRequests) {
			Long productId = orderDetailRequest.getProductId();
			OrderDetailRequest existingOrderDetailRequest = productOrderDetailMap.get(productId);
			if (existingOrderDetailRequest != null) {
				int quantity = existingOrderDetailRequest.getQuantity() + orderDetailRequest.getQuantity();
				existingOrderDetailRequest.setQuantity(quantity);
			} else {
				productOrderDetailMap.put(productId, orderDetailRequest);
			}
		}
	}

}
