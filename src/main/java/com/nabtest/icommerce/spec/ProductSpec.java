package com.nabtest.icommerce.spec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.nabtest.icommerce.contant.Constant;
import com.nabtest.icommerce.entity.Product;

public final class ProductSpec {
	
	private ProductSpec() {}

	public static Specification<Product> filterByNameLike(String name) {
		return (root, query, criteriaBuilder) -> {
			if(StringUtils.isBlank(name)) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.like(root.get(Constant.PRODUCT_NAME), "%" + name + "%");
		};
	}

	public static Specification<Product> filterByPrice(Double price) {
		return (root, query, criteriaBuilder) -> {
			if(price == null) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.equal(root.get(Constant.PRODUCT_PRICE), price);
		};
	}

	public static Specification<Product> filterByBrandLike(String brand) {
		return (root, query, criteriaBuilder) -> {
			if(StringUtils.isBlank(brand)) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.like(root.get(Constant.PRODUCT_BRAND), "%" + brand + "%");
		};
	}

	public static Specification<Product> filterByColor(String color) {
		return (root, query, criteriaBuilder) -> {
			if(StringUtils.isBlank(color)) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.equal(root.get(Constant.PRODUCT_PRICE), color);
		};
	}
	
	public static Specification<Product> filterByPriceFrom(Double priceFrom) {
		return (root, query, criteriaBuilder) -> {
			if(priceFrom == null) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.greaterThanOrEqualTo(root.get(Constant.PRODUCT_PRICE), priceFrom);
		};
	}
	
	public static Specification<Product> filterByPriceTo(Double priceTo) {
		return (root, query, criteriaBuilder) -> {
			if(priceTo == null) {
				return criteriaBuilder.conjunction();
			}
			return criteriaBuilder.lessThanOrEqualTo(root.get(Constant.PRODUCT_PRICE), priceTo);
		};
	}
}
