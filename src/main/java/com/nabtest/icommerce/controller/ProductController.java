package com.nabtest.icommerce.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nabtest.icommerce.contant.Constant;
import com.nabtest.icommerce.contant.MessageConstant;
import com.nabtest.icommerce.dto.filter.FilterResult;
import com.nabtest.icommerce.dto.filter.ProductFilter;
import com.nabtest.icommerce.dto.request.ProductRequest;
import com.nabtest.icommerce.dto.response.APIResponse;
import com.nabtest.icommerce.dto.response.ProductResponse;
import com.nabtest.icommerce.service.ProductService;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public APIResponse<FilterResult<ProductResponse>> getProducts(@Min(0) @RequestParam(name = "page", defaultValue = "0") int page,
			@Min(1) @RequestParam(name = "amount", defaultValue = "25", required = false) int amount,
			@RequestParam(name = Constant.PRODUCT_NAME, required = false) String name,
			@Min(1) @RequestParam(name = Constant.PRODUCT_PRICE, required = false) Double price,
			@Min(1) @RequestParam(name = Constant.PRODUCT_PRICE_FROM, required = false) Double priceFrom,
			@Min(1) @RequestParam(name = Constant.PRODUCT_PRICE_TO, required = false) Double priceTo,
			@RequestParam(name = Constant.PRODUCT_BRAND, required = false) String brand,
			@RequestParam(name = Constant.PRODUCT_COLOR, required = false) String color,
			@RequestParam(name = Constant.ORDER, required = false) String order,
			@RequestParam(name = Constant.ORDER_BY, required = false) String orderBy) {
		
		ProductFilter filter = new ProductFilter(page, amount);
		
		filter.setName(name);
		filter.setColor(color);
		filter.setPrice(price);
		filter.setBrand(brand);
		filter.setPriceFrom(priceFrom);
		filter.setPriceTo(priceTo);
		filter.setOrder(order);
		filter.setOrderBy(orderBy);
		FilterResult<ProductResponse> filterResult = productService.get(filter);
		return new APIResponse<>(filterResult, System.currentTimeMillis());
	}
	
	@GetMapping(path = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public APIResponse<ProductResponse> getProductById(@PathVariable(value = "id") Long productId) {
		ProductResponse productResponseDTO = productService.get(productId);
		return new APIResponse<>(productResponseDTO, System.currentTimeMillis());
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public APIResponse<ProductResponse> addProduct(@Valid @RequestBody ProductRequest productAddingRequest) {
		ProductResponse productResponseDTO = productService.checkAndCreate(productAddingRequest);
		return new APIResponse<>(productResponseDTO, System.currentTimeMillis());
	}

	@PutMapping(path = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public APIResponse<ProductResponse> updateProduct(@PathVariable(value = "id") Long productId, @Valid @RequestBody ProductRequest productUpdatingRequest) {
		ProductResponse productResponseDTO = productService.checkAndUpdate(productId, productUpdatingRequest);
		return new APIResponse<>(productResponseDTO, System.currentTimeMillis());
	}

	@DeleteMapping(path = "/{id}")
	public APIResponse<String> deleteProduct(@PathVariable(value = "id") long productId) {
		productService.delete(productId);
		return new APIResponse<>(MessageConstant.PRODUCT_WAS_DELETED_SUCCESSFULLY, System.currentTimeMillis());
	}
}
