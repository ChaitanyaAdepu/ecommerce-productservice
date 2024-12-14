package com.ecommerce.product.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.product.aspect.Loggable;
import com.ecommerce.product.dao.ProductRepo;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.exception.AppServiceException;
import com.ecommerce.product.interfaces.ProductService;
import com.ecommerce.product.model.APIResponse;
import com.ecommerce.product.model.ProductRequest;
import com.ecommerce.product.model.ProductResponse;

@Service

public class ProductServiceImpl<T> implements ProductService<T>{

	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepo productRepo;

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
				.quantity(productInput.getQuantity())
				.build();
		//		Product product = new Product();
		//		product.setProductName(productInput.getName());
		//		product.setPrice(productInput.getPrice());
		//		product.setQuantity(productInput.getQuantity());
		//		
		productRepo.save(product);

		return product.getProductId();
	}
	@Override
	@Loggable
	public ProductResponse getProducts(Long productId) {
		ProductResponse pr = new ProductResponse();
		Optional<Product>  productOpt = productRepo.findById(productId);
		if(!productOpt.isPresent()) {
			throw new AppServiceException("product ID not found.","ERR404");
		}
		Product product = productOpt.get();
		pr.setProductId(product.getProductId());
		pr.setProductName(product.getProductName());
		return pr;
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
