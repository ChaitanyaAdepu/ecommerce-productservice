package com.ecommerce.product.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.commonlib.exception.AppServiceException;
import com.ecommerce.product.aspect.Loggable;
import com.ecommerce.product.dao.CategoryRepo;
import com.ecommerce.product.dao.ProductImagesRepo;
import com.ecommerce.product.dao.ProductRepo;
import com.ecommerce.product.dao.ProductReviewsRepo;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductImages;
import com.ecommerce.product.interfaces.ProductService;
import com.ecommerce.product.model.APIResponse;
import com.ecommerce.product.model.ProductRequest;
import com.ecommerce.product.model.ProductResponse;
import com.ecommerce.product.model.ProductsDTO;

@Service

public class ProductServiceImpl<T> implements ProductService<T>{

	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ProductImagesRepo imagesRepo;
	
	@Autowired
	private ProductReviewsRepo reviewsRepo;

	@Override
	public void test() {
		System.out.println("test");
	}

	@Override
	@Loggable
	public Long addProduct(ProductRequest productInput) {

		Product product = Product.builder()
				.productName(productInput.getName())
				.price(productInput.getPrice())
				.code(productInput.getCode())
				.quantity(productInput.getQuantity())
				.category(getCategoryByCode(productInput.getCategory()))
				.build();
		setCommonFields(product);
		//		Product product = new Product();
		//		product.setProductName(productInput.getName());
		//		product.setPrice(productInput.getPrice());
		//		product.setQuantity(productInput.getQuantity());
		//		
		productRepo.save(product);

		return product.getProductId();
	}
	private Category getCategoryByCode(String code) {
		Optional<Category> categoryOpt = categoryRepo.findByCode(code);
		return categoryOpt.isPresent() ? categoryOpt.get() : null;
	}
	
	private void setCommonFields(Product product) {
		product.setCreatedOn(Timestamp.from(Instant.now() ));
		product.setCreatedBy("SYSTEM");
	}
	@Override
	@Loggable
	public ProductResponse getProducts(String category, int page, int perPage) {
		
		
		if(category != null) {
			return getProductsByCategory(category, page, perPage);
		}
		return getAllProducts(page, perPage);
    }
	@Override
	public ProductResponse getProductByProductId(Long productId) {
		ProductResponse pr = new ProductResponse();
		Optional<Product>  productOpt = productRepo.findById(productId);
		if(!productOpt.isPresent()) {
			throw new AppServiceException("product ID not found.","ERR404");
		}
		Product product = productOpt.get();
		
		ProductsDTO singleProduct = new ProductsDTO();
		singleProduct.setProductId(product.getProductId());
		singleProduct.setProductName(product.getProductName());
		singleProduct.setRating(getProductAvgRating(product));
		singleProduct.setImageURL(getProductImageURL(product));
		List<ProductsDTO> listProducts = new ArrayList<>();
		listProducts.add(singleProduct);
		
		pr.setProducts(listProducts);
		return pr;
	}
	@Loggable
	private ProductResponse getProductsByCategory(String category, int page, int perPage) {
		ProductResponse pr = new ProductResponse();
		List<Product>  productList = productRepo.findByCategory(category);
		if(productList.isEmpty()) {
			throw new AppServiceException("category not found.","ERR404");
		}
		List<ProductsDTO> listProducts = new ArrayList<>();

        for(Product product : productList) {
        	ProductsDTO singleProduct = new ProductsDTO();
    		singleProduct.setProductId(product.getProductId());
    		singleProduct.setProductName(product.getProductName());
    		singleProduct.setQuantity(product.getQuantity());
    		singleProduct.setPrice(product.getPrice());
    		singleProduct.setRating(getProductAvgRating(product));
    		singleProduct.setImageURL(getProductImageURL(product));
    		listProducts.add(singleProduct);

        }
		
		pr.setProducts(listProducts);
		
		return pr;
	}
	@Loggable
	private ProductResponse getAllProducts(int page, int perPage) {
		ProductResponse pr = new ProductResponse();
		List<Product>  productList = productRepo.findAll();
		if(productList.isEmpty()) {
			throw new AppServiceException("category not found.","ERR404");
		}
		List<ProductsDTO> listProducts = new ArrayList<>();

        for(Product product : productList) {
        	ProductsDTO singleProduct = new ProductsDTO();
    		singleProduct.setProductId(product.getProductId());
    		singleProduct.setProductName(product.getProductName());
    		singleProduct.setQuantity(product.getQuantity());
    		singleProduct.setPrice(product.getPrice());
    		singleProduct.setRating(getProductAvgRating(product));
    		singleProduct.setImageURL(getProductImageURL(product));
    		listProducts.add(singleProduct);

        }
		
		pr.setProducts(listProducts);
		return pr;
	}
	private Float getProductAvgRating(Product product) {
		return reviewsRepo.findAvgRatingByProduct(product);
	}
	private String[] getProductImageURL(Product product) {
		List<String> list = imagesRepo.findImageUrlsByProduct(product);
		if(list.isEmpty()) {
	        return null; // Return an array with "NA" if no URLs are found
		}
		return list.toArray(new String[0]);
	}
	

//	@Override
//	@Loggable
//	public APIResponse<T> getProducts(Long productId) {
//		List<Product> listProd = new ArrayList<>();
//		APIResponse<Product> apiResp = new APIResponse<>();
//
//
//		if(productId != null) {
//			Optional<Product>  productOpt = productRepo.findById(productId);
//			if(!productOpt.isPresent()) {
//				throw new AppServiceException("product ID not found.","ERR404");
//			}
//			apiResp.setMessage("ProductID found.");
//			apiResp.setTotalItems(1+"");
//			listProd.add(productOpt.get());
//		}
//		else {
//			listProd =  productRepo.findAll();
//			apiResp.setMessage("get all products.");
//			apiResp.setTotalItems(listProd.size()+"");
//			apiResp.setResponseList(listProd);
//
//		}
//		return (APIResponse<T>) apiResp;
//	}

	@Override
	public APIResponse<T> reduceQuantity(Long productId, Long quantity) {

		Product product = productRepo.findById(productId).orElseThrow(
				() -> new AppServiceException("product ID doesnt exist", "ERR404")
				);
		if(product.getQuantity() < quantity) {
			throw new AppServiceException("product doesnt have insufficient quantity", "ERR400");
		}
		product.setQuantity(product.getQuantity() - quantity);
		productRepo.save(product);
		APIResponse<T> apiResp = new APIResponse<>();
		apiResp.setMessage("OK");

		return apiResp;
	}


}
