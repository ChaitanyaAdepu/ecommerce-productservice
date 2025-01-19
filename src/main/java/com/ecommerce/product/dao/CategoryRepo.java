package com.ecommerce.product.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.product.entity.Category;

public interface CategoryRepo extends JpaRepository<Category,Long>{
	
	Optional<Category> findByCode(String code);
}
