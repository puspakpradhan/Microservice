package com.example.demo.dto;

import java.util.List;

public class ProductCategoryConversionBean {

	
	private int catId;
	private String categoryName;
	private List<ProductBean> products;
	
	public ProductCategoryConversionBean() {
		
	}
	
	public ProductCategoryConversionBean(int catId, String categoryName,List<ProductBean> products) {
		super();
		this.catId = catId;
		this.categoryName = categoryName;
		this.products = products;
	}
	
	
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public List<ProductBean> getProducts() {
		return products;
	}
	public void setProducts(List<ProductBean> products) {
		this.products = products;
	}
	
	
	
	
}
