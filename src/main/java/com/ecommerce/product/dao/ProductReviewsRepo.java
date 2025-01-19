package com.ecommerce.product.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductReviews;

@Repository
public interface ProductReviewsRepo extends JpaRepository<ProductReviews, Long>{
	
	@Query("SELECT AVG(r.rating) FROM ProductReviews r WHERE r.product = ?1")
	Float findAvgRatingByProduct(Product product);
}
