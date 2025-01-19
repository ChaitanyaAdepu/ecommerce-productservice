package com.ecommerce.product.model;

import lombok.Data;

@Data
public class ProductRequest {
	
	private String name;
	private Float price;
	private Long quantity;
	private String category;
	private String code;
	
}
