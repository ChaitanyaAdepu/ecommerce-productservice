package com.ecommerce.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product.interfaces.ProductService;

@RestController
public class HomeController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/")
	public String hello() {
		productService.test();
		return "Hello Product Service";
	}
}
