package com.ecommerce.product.controller;

import java.lang.annotation.Repeatable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product.interfaces.ProductService;
import com.ecommerce.product.model.APIResponse;
import com.ecommerce.product.model.ProductRequest;
import com.ecommerce.product.service.ProductServiceImpl;

@RestController
@RequestMapping("/product")
public class ProductController<T> {
	
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/add")
	public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productReq) {
		log.info("add product");
		long productId = productService.addProduct(productReq);
		return new ResponseEntity<>(productId, HttpStatus.CREATED);
		
	}
	
	@GetMapping
	public APIResponse<T> getProducts(@RequestParam(required = false) Long productId){
		return productService.getProducts(productId);
	}
	@PostMapping("/reduce-quantity/{id}")
	public ResponseEntity<APIResponse<T>> reduceQuantity(@PathVariable("id") Long productId, @RequestParam Long quantity){
		return new ResponseEntity<>(productService.reduceQuantity(productId, quantity), HttpStatus.OK);
	}
}
