package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductCategory;
import com.example.demo.entity.ProductNew;
import com.example.demo.repository.ProductCategoryRepository;
import com.example.demo.repository.ProductRepository;

@CrossOrigin(origins ="http://localhost:3000")
@RestController
public class ProductController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	@Autowired
	private RestTemplate template;
	
	
	//Added for New starts
	@GetMapping("/productsNew")
	  public List<ProductNew> getAllproductsNew(){
		
		logger.info("[Producr Controller]-----[getAllProducts() ------" );
		  
		  List<ProductNew> productNew = null;
	    
	    try {
	    	productNew = productRepository.getAllProductsNew();
	    }catch(Exception ex) {
	    	logger.error("[Producr Controller]-----[getAllProducts() --- [Exception While fetching all products]-----"+ex.getMessage() );
	    	ex.printStackTrace();
	    }
	    
	    return productNew;
	  }
	
	
	@GetMapping("/productCode/{code}")
	  public ProductNew getProductByCode(@PathVariable String code){
		ProductNew productByCode = null;
	    try {
	    	productByCode = productRepository.getProductByCode(code);
	    	
	    	if(null == productByCode ) {
	    		return new ProductNew();
	    	}
	    }catch(Exception ex) {
	    	return new ProductNew();
	    }
	    
	    return productByCode;
	  }

	
	//Added for New end
	
	@GetMapping("/products")
	  public List<ProductCategory> getAllproducts(){
		
		logger.info("[Producr Controller]-----[getAllProducts() ------" );
		  
		  List<ProductCategory> productCategories = null;
	    
	    try {
	    	productCategories = productCategoryRepository.findAll();
	    }catch(Exception ex) {
	    	logger.error("[Producr Controller]-----[getAllProducts() --- [Exception While fetching all products]-----"+ex.getMessage() );
	    	ex.printStackTrace();
	    }
	    
	    return productCategories;
	  }
	
	
	@GetMapping("/allProducts")//End point swaped line 25-40
	  public List<Product> getproducts(){
		  
		  List<Product> products = null;
	    
	    try {
	    	products = productRepository.getAllProducts();
	    }catch(Exception ex) {
	    	
	    }
	    
	    return products;
	  }
	
	
	@GetMapping("/product/{id}")
	  public Product getConversionFactorByCountryCode(@PathVariable int id){
		Product productById = null;
	    try {
	    	productById = productRepository.getProductById(id);
	    	
	    	if(null == productById ) {
	    		return new Product();
	    	}
	    }catch(Exception ex) {
	    	return new Product();
	    }
	    
	    return productById;
	  }

}
