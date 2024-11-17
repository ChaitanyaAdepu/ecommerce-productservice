package com.ecommerce.product.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.product.entity.Product;

public interface ProductRepo extends JpaRepository<Product,Long>{
	
}
