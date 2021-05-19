package com.nabtest.icommerce.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import com.nabtest.icommerce.dto.request.ProductRequest;
import com.nabtest.icommerce.dto.response.ProductResponse;
import com.nabtest.icommerce.entity.Product;
import com.nabtest.icommerce.enums.ErrorType;
import com.nabtest.icommerce.exception.ValidationException;
import com.nabtest.icommerce.repository.ProductRepository;


@SpringBootTest
public class ProductServiceImplTest {

	Logger logger = LoggerFactory.getLogger(ProductServiceImplTest.class);
	
	@Mock
	ProductRepository productRepository;

	@InjectMocks
	private ProductServiceImpl productService;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void checkAndCreate_create_product_successfully() {
		ProductRequest productAddingRequest = new ProductRequest();
		productAddingRequest.setName("product1");
		productAddingRequest.setPrice(10000.0);
		productAddingRequest.setColor("red");
		productAddingRequest.setQuantity(1000);
		productAddingRequest.setBrand("Brand1");

		Product productEntity = new Product();
		BeanUtils.copyProperties(productAddingRequest, productEntity);
		productEntity.setId(1L);
		BDDMockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(productEntity);

		ProductResponse productResponse = productService.checkAndCreate(productAddingRequest);

		assertEquals(productEntity.getId(), productResponse.getId());
		assertEquals(productEntity.getName(), productResponse.getName());
		assertEquals(productEntity.getPrice(), productResponse.getPrice());
		assertEquals(productEntity.getBrand(), productResponse.getBrand());
		assertEquals(productEntity.getQuantity(), productResponse.getQuantity());
		assertEquals(productEntity.getColor(), productResponse.getColor());
	}

	@Test
	public void checkAndCreate_ExistedProduct_Then_ProductName_IsExisted_Exception() {
		ProductRequest productAddingRequest = new ProductRequest();
		productAddingRequest.setName("product1");
		productAddingRequest.setPrice(10000.0);
		productAddingRequest.setColor("red");
		productAddingRequest.setQuantity(1000);
		productAddingRequest.setBrand("Brand1");

		Product productEntity = new Product();
		BeanUtils.copyProperties(productAddingRequest, productEntity);
		productEntity.setId(1L);

		BDDMockito.when(productRepository.existsByName("product1")).thenReturn(true);

		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.checkAndCreate(productAddingRequest);
		});

		String expectMGS = ErrorType.ERROR_PRODUCT_NAME_EXISTS.getMessage();
		String actualMessage = exception.getMessage();
		assertEquals(expectMGS, actualMessage);
	}

	@Test
	public void checkAndUpdate_ExistedProduct_CanUpdate() {
		ProductRequest productAddingRequest = new ProductRequest();
		productAddingRequest.setName("product1");
		productAddingRequest.setPrice(10000.0);
		productAddingRequest.setColor("red");
		productAddingRequest.setQuantity(1000);
		productAddingRequest.setBrand("Brand1");

		Product productEntity = new Product();
		BeanUtils.copyProperties(productAddingRequest, productEntity);

		Product productDB = new Product(12L, "product1", 12000.0, "Brand1", 3);
		Optional<Product> existedProduct = Optional.of(productDB);

		BDDMockito.when(productRepository.findById(12L)).thenReturn(existedProduct);

		BDDMockito.when(productRepository.existsByNameAndIdNot("product1", 12L)).thenReturn(false);
		productDB.setPrice(productAddingRequest.getPrice());
		productDB.setName(productAddingRequest.getName());
		productDB.setColor(productAddingRequest.getColor());
		productDB.setQuantity(productAddingRequest.getQuantity());
		productDB.setBrand(productAddingRequest.getBrand());
		BDDMockito.when(productRepository.save(productDB)).thenReturn(productDB);

		ProductResponse productResponse = productService.checkAndUpdate(12L, productAddingRequest);

		assertEquals(productDB.getId(), productResponse.getId());
		assertEquals(productDB.getName(), productResponse.getName());
		assertEquals(productDB.getPrice(), productResponse.getPrice());
		assertEquals(productDB.getQuantity(), productResponse.getQuantity());
		assertEquals(productDB.getColor(), productResponse.getColor());
		assertEquals(productDB.getBrand(), productResponse.getBrand());
	}

	@Test
	public void checkAndUpdate_NotExistedProductID_CanNotUpdate_ExpectProductNotFoundException() {
		ProductRequest productUpdateRequest = new ProductRequest();
		productUpdateRequest.setName("product");
		productUpdateRequest.setPrice(12000.0);
		productUpdateRequest.setQuantity(10);
		Product productEntity = new Product();
		BeanUtils.copyProperties(productUpdateRequest, productEntity);

		BDDMockito.when(productRepository.findById(12L)).thenReturn(Optional.<Product>empty());

		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.checkAndUpdate(12L, productUpdateRequest);
		});

		String expectMGS = ErrorType.ERROR_PRODUCT_NOT_FOUND.getMessage();
		String actualMessage = exception.getMessage();
		assertEquals(expectMGS, actualMessage);
	}

	@Test
	public void checkAndUpdate_ExistedProductName_CanNotUpdate_ExpectProductNameIsExistedException() {
		ProductRequest productUpdateRequest = new ProductRequest();
		productUpdateRequest.setName("product");
		productUpdateRequest.setPrice(12000.0);
		productUpdateRequest.setQuantity(10);
		Product productEntity = new Product();
		BeanUtils.copyProperties(productUpdateRequest, productEntity);

		Product productDB = new Product(12L, "product", 12000.0, "brand1", 3);
		Optional<Product> existedProduct = Optional.of(productDB);

		BDDMockito.when(productRepository.findById(12L)).thenReturn(existedProduct);
		BDDMockito.when(productRepository.existsByNameAndIdNot("product", 12L)).thenReturn(true);

		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.checkAndUpdate(12L, productUpdateRequest);
		});

		String expectMGS = ErrorType.ERROR_PRODUCT_NAME_EXISTS.getMessage();
		String actualMessage = exception.getMessage();
		assertEquals(expectMGS, actualMessage);
	}

	@Test
	public void checkAndUpdate_ExitedProductID_CanGetProductData() {
		Product productDB = new Product(12L, "product", 12000.0, "brand1", 3);
		Optional<Product> existedProduct = Optional.of(productDB);
		BDDMockito.when(productRepository.findById(12L)).thenReturn(existedProduct);
		ProductResponse response = productService.get(12L);

		assertEquals(productDB.getId(), response.getId());
		assertEquals(productDB.getName(), response.getName());
		assertEquals(productDB.getPrice(), response.getPrice());
		assertEquals(productDB.getBrand(), response.getBrand());
		assertEquals(productDB.getQuantity(), response.getQuantity());
	}

	@Test
	public void checkAndUpdate_NotExitedProductID_CanNotGet_NotFoundProductException() {

		Optional<Product> notExistedProduct = Optional.<Product>empty();
		BDDMockito.when(productRepository.findById(12L)).thenReturn(notExistedProduct);

		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.get(12L);
		});

		String expectMGS = ErrorType.ERROR_PRODUCT_NOT_FOUND.getMessage();
		String actualMessage = exception.getMessage();
		assertEquals(expectMGS, actualMessage);
	}


	@Test
	public void checkAndUpdate_ExitedProductID_CanBeDeleted() {
		Product product = new Product(12L, "Product 1", 12000.0, "Product 1's brand", 3);
		Optional<Product> existedProduct = Optional.of(product);

		BDDMockito.when(productRepository.findById(12L)).thenReturn(existedProduct);
		BDDMockito.willDoNothing().given(productRepository).delete(product);
		productService.delete(12L);
	}

	@Test
	public void checkAndUpdate_NotExitedProductID_CanNotDelete_NotFoundProductException() {
		Optional<Product> notExistedProduct = Optional.<Product>empty();

		BDDMockito.when(productRepository.findById(12L)).thenReturn(notExistedProduct);

		Exception exception = assertThrows(ValidationException.class, () -> {
			productService.delete(12L);
		});

		String expectMGS = ErrorType.ERROR_PRODUCT_NOT_FOUND.getMessage();
		String actualMessage = exception.getMessage();
		assertEquals(expectMGS, actualMessage);
	}

}
