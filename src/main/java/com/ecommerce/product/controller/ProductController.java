package com.ecommerce.product.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.ecommerce.product.model.CategoriesDTO;
import com.ecommerce.product.model.ListCategories;
import com.ecommerce.product.model.ProductRequest;
import com.ecommerce.product.model.ProductResponse;

@RestController
@RequestMapping("/products")
public class ProductController<T> {

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);


	@Autowired
	private ProductService<T> productService;

	@PostMapping("/add")
	public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productReq) {
		log.info("add product");
		long productId = productService.addProduct(productReq);
		return new ResponseEntity<>(productId, HttpStatus.CREATED);

	}

	@GetMapping
	public ProductResponse getProducts(@RequestParam(required = false) Long productId, 
			@RequestParam(required = false) String category,
			@RequestParam(required = false,defaultValue = "0") int page,
			@RequestParam(required = false,defaultValue = "10") int perPage){

		return productService.getProducts(productId, category, page, perPage);

	}
	@GetMapping("/categories")
	public CategoriesDTO getProductCategories(){

		CategoriesDTO dto = new CategoriesDTO();
		List<ListCategories> list = new ArrayList<>();
		ListCategories lc = new ListCategories();
		lc.setCategoryCode("MOBILE");
		lc.setCategoryName("Mobiles");
		list.add(lc);
		
		lc = new ListCategories();
		lc.setCategoryCode("LAPTOPS");
		lc.setCategoryName("Laptops");
		list.add(lc);
		
		lc = new ListCategories();
		lc.setCategoryCode("BOOKS");
		lc.setCategoryName("Books");
		list.add(lc);
		
		dto.setCategories(list);
		return dto;

	}
	@PostMapping("/reduce-quantity/{id}")
	public ResponseEntity<APIResponse<T>> reduceQuantity(@PathVariable("id") Long productId, @RequestParam Long quantity){
		return new ResponseEntity<>(productService.reduceQuantity(productId, quantity), HttpStatus.OK);
	}
}
