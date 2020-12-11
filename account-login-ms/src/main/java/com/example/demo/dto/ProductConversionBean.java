package com.example.demo.dto;

public class ProductConversionBean {
	
	
	private int id;
	private String productName;
	private String productDesc;
	private int price;
//	private String statusMessage;
//	private int statusCode;
	
	public ProductConversionBean() {
		
	}
	
	public ProductConversionBean(int id, String productName, String productDesc, int price
			) {
		super();
		this.id = id;
		this.productName = productName;
		this.productDesc = productDesc;
		this.price = price;
//		this.statusMessage = statusMessage;
//		this.statusCode = statusCode;
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

//	public String getStatusMessage() {
//		return statusMessage;
//	}
//
//	public void setStatusMessage(String statusMessage) {
//		this.statusMessage = statusMessage;
//	}
//
//	public int getStatusCode() {
//		return statusCode;
//	}
//
//	public void setStatusCode(int statusCode) {
//		this.statusCode = statusCode;
//	}
//	
	
	
	

	
}
