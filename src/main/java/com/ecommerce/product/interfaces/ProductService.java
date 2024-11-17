package com.ecommerce.product.interfaces;

import com.ecommerce.product.model.APIResponse;
import com.ecommerce.product.model.ProductRequest;

public interface ProductService<T> {
	void test();
	
	Long addProduct(ProductRequest productInput);
	
	APIResponse<T> getProducts(Long productId);
	
	APIResponse<T> reduceQuantity(Long productId, Long quantity);
}