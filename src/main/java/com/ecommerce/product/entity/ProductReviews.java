package com.ecommerce.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "reviews",schema = "products")
public class ProductReviews {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	private String title;
	
	private String description;
	
	private Float rating;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false) 
	private Product product;
	
	private String authorName;
	
	private Long authorUserId;
	
	@Column(name = "flag_suspicious")
	private boolean isReviewFlaggedSuspicious;
}
