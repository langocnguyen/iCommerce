package com.nabtest.icommerce.service;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.nabtest.icommerce.dto.filter.FilterResult;
import com.nabtest.icommerce.dto.filter.ProductFilter;
import com.nabtest.icommerce.dto.request.ProductRequest;
import com.nabtest.icommerce.dto.response.ProductResponse;
import com.nabtest.icommerce.entity.Product;

@Validated
public interface ProductService {
	public ProductResponse checkAndCreate(@Valid @NotNull ProductRequest productAddingRequest);

	public ProductResponse checkAndUpdate(@NotNull @Min(1) Long id, @Valid @NotNull ProductRequest productUpdatingRequest);

	public ProductResponse get(@NotNull @Min(1) Long id);

	public void delete(@NotNull @Min(1) Long id);
	
	public FilterResult<ProductResponse> get(ProductFilter filter);
	
	public ProductResponse transformToResponse(Product productEntity);
	
	public Product getEntity(@NotNull @Min(1) Long id);
	
	public int countNumberOfSellingProduct(@NotNull @Min(1) Long id);
}
