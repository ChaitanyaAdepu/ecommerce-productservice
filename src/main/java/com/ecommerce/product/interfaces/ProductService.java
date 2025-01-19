package com.ecommerce.product.interfaces;

import com.ecommerce.product.model.APIResponse;
import com.ecommerce.product.model.ProductRequest;
import com.ecommerce.product.model.ProductResponse;

public interface ProductService<T> {
	void test();
	
	Long addProduct(ProductRequest productInput);
	
//	APIResponse<T> getProducts(Long productId);

	ProductResponse getProducts(String category, int page, int perPage);
	
	ProductResponse getProductByProductId(Long productId);


	APIResponse<T> reduceQuantity(Long productId, Long quantity);
}
