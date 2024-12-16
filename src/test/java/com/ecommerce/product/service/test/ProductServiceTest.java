package com.ecommerce.product.service.test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.product.dao.ProductRepo;
import com.ecommerce.product.exception.AppServiceException;
import com.ecommerce.product.service.ProductServiceImpl;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {



	@Mock
	private ProductRepo productRepo;

	@InjectMocks
//	@Autowired
	private ProductServiceImpl productService;

	@Test
	void testThatGetProductIsRetrived() {

		assertThrows(AppServiceException.class, () -> productService.getProducts(1L));	
	}
}
