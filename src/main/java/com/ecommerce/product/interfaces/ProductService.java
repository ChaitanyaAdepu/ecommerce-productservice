package com.ecommerce.product.interfaces;

import com.ecommerce.product.model.APIResponse;
import com.ecommerce.product.model.ProductRequest;
import com.ecommerce.product.model.ProductResponse;

public interface ProductService<T> {
	void test();
	
	Long addProduct(ProductRequest productInput);
	
//	APIResponse<T> getProducts(Long productId);

	ProductResponse getProducts(Long productId, String category, int page, int perPage);

	APIResponse<T> reduceQuantity(Long productId, Long quantity);
}
