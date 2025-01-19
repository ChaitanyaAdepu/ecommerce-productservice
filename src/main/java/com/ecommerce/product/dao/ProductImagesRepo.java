package com.ecommerce.product.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductImages;

@Repository
public interface ProductImagesRepo extends JpaRepository<ProductImages, Long>{
	
	List<ProductImages> findByProduct(Product productId);
	
	@Query("SELECT pi.imageUrl FROM ProductImages pi WHERE pi.product = :product")
	List<String> findImageUrlsByProduct(@Param("product") Product product);
}
