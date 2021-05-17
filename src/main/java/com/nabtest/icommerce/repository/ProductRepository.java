package com.nabtest.icommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nabtest.icommerce.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	boolean existsByName(String name);

	boolean existsByNameAndIdNot(String name, Long id);
	
	@Query("SELECT SUM(o.quantity) FROM OrderDetail o WHERE o.productId = :id")
	Integer countNumberOfSellingProduct(Long id);
}
