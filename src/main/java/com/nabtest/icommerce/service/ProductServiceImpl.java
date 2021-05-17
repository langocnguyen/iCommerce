package com.nabtest.icommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nabtest.icommerce.contant.Constant;
import com.nabtest.icommerce.dto.filter.FilterDTO;
import com.nabtest.icommerce.dto.filter.FilterResult;
import com.nabtest.icommerce.dto.filter.ProductFilter;
import com.nabtest.icommerce.dto.request.ProductRequest;
import com.nabtest.icommerce.dto.response.ProductResponse;
import com.nabtest.icommerce.entity.Product;
import com.nabtest.icommerce.enums.ErrorType;
import com.nabtest.icommerce.exception.ValidationException;
import com.nabtest.icommerce.repository.ProductRepository;
import com.nabtest.icommerce.spec.ProductSpec;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	private static final String[] ORDER_FIELDS = { Constant.PRODUCT_BRAND, Constant.PRODUCT_COLOR, Constant.PRODUCT_ID,
			Constant.PRODUCT_NAME, Constant.PRODUCT_PRICE };

	@Override
	public ProductResponse checkAndCreate(ProductRequest productAddingRequest) {
		// Check name is existed
		String name = productAddingRequest.getName();
		boolean isExistedByName = productRepository.existsByName(name);
		if (isExistedByName) {
			throw new ValidationException(ErrorType.ERROR_PRODUCT_NAME_EXISTS);
		}

		// Create product
		Product productEntity = new Product();
		BeanUtils.copyProperties(productAddingRequest, productEntity);
		productEntity = productRepository.save(productEntity);
		return transformToResponse(productEntity);
	}

	@Override
	public ProductResponse checkAndUpdate(Long id, ProductRequest productUpdatingRequest) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		Product productEntity = null;
		// Check product is existed
		if (optionalProduct.isPresent()) {
			productEntity = optionalProduct.get();
		} else {
			throw new ValidationException(ErrorType.ERROR_PRODUCT_NOT_FOUND);
		}

		// Check name is existed
		String name = productUpdatingRequest.getName();
		boolean isExistedByName = productRepository.existsByNameAndIdNot(name, id);
		if (isExistedByName) {
			throw new ValidationException(ErrorType.ERROR_PRODUCT_NAME_EXISTS);
		}

		// Update product
		productEntity.setName(name);
		productEntity.setPrice(productUpdatingRequest.getPrice());
		productEntity.setBrand(productUpdatingRequest.getBrand());
		productEntity.setColor(productUpdatingRequest.getColor());
		productEntity.setQuantity(productUpdatingRequest.getQuantity());
		productEntity = productRepository.save(productEntity);
		return transformToResponse(productEntity);
	}

	@Override
	public ProductResponse get(Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if (optionalProduct.isPresent()) {
			return transformToResponse(optionalProduct.get());
		}

		throw new ValidationException(ErrorType.ERROR_PRODUCT_NOT_FOUND);
	}

	@Override
	public void delete(Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if (optionalProduct.isPresent()) {
			productRepository.delete(optionalProduct.get());
			return;
		}
		throw new ValidationException(ErrorType.ERROR_PRODUCT_NOT_FOUND);
	}

	@Override
	public FilterResult<ProductResponse> get(ProductFilter filter) {
		checkFilterOrder(filter);
		String order = filter.getOrder();
		String orderBy = filter.getOrderBy();
		PageRequest pageRequest = null;

		// Check order and order by parameter
		if (StringUtils.isAllBlank(order, orderBy)) {
			pageRequest = PageRequest.of(filter.getPage(), filter.getAmount());
		} else {
			Sort sort = Sort.by(Sort.Direction.valueOf(StringUtils.upperCase(order)), orderBy);
			pageRequest = PageRequest.of(filter.getPage(), filter.getAmount(), sort);
		}

		// Filter by condition
		Specification<Product> productSpec = Specification.where(ProductSpec.filterByNameLike(filter.getName()))
				.and(ProductSpec.filterByColor(filter.getColor())).and(ProductSpec.filterByPrice(filter.getPrice()))
				.and(ProductSpec.filterByBrandLike(filter.getBrand()))
				.and(ProductSpec.filterByPriceFrom(filter.getPriceFrom()))
				.and(ProductSpec.filterByPriceTo(filter.getPriceTo()));

		Page<Product> page = productRepository.findAll(productSpec, pageRequest);
		List<Product> products = page.getContent();
		List<ProductResponse> productResponses = new ArrayList<>();
		if (!CollectionUtils.isEmpty(products)) {
			products.forEach(product -> productResponses.add(transformToResponse(product)));
		}
		return new FilterResult<>(productResponses, page.getTotalElements());
	}

	@Override
	public ProductResponse transformToResponse(Product productEntity) {
		ProductResponse productResponse = new ProductResponse();
		BeanUtils.copyProperties(productEntity, productResponse);
		return productResponse;
	}

	@Override
	public Product getEntity(Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if (optionalProduct.isPresent()) {
			return optionalProduct.get();
		}
		return null;
	}

	@Override
	public int countNumberOfSellingProduct(Long id) {
		return Optional.ofNullable(productRepository.countNumberOfSellingProduct(id)).orElse(0);
	}

	private void checkFilterOrder(FilterDTO filter) {
		String order = filter.getOrder();
		String orderBy = filter.getOrderBy();
		if (StringUtils.isAllBlank(order, orderBy)) {
			return;
		}

		if (StringUtils.isBlank(order)
				|| !StringUtils.equalsAnyIgnoreCase(order, Sort.Direction.ASC.name(), Sort.Direction.DESC.name())) {
			throw new ValidationException(ErrorType.ERROR_REQUEST_ORDER_PARAM_IS_INVALID);
		}

		if (StringUtils.isBlank(orderBy) || !StringUtils.equalsAny(orderBy, ORDER_FIELDS)) {
			throw new ValidationException(ErrorType.ERROR_REQUEST_ORDER_BY_PARAM_IS_INVALID);
		}
	}

}
