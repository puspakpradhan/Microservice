package com.example.demo.dto;

public class ProductBean {
	
	
	private int id;
	private String productName;
	private String productDesc;
	private int price;
	
	public ProductBean() {
		
	}
	
	public ProductBean(int id, String productName, String productDesc, int price) {
		super();
		this.id = id;
		this.productName = productName;
		this.productDesc = productDesc;
		this.price = price;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	

}
