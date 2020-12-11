package com.example.demo.dto;

public class CartNewBean {
	
	
	private String prodCode;
	private String userID;
	private String prodName;
	private double price;
	private int quantity;
	private double totalAmount;
	
	
	public CartNewBean() {
		
	}

	public CartNewBean(String prodCode, String userID, String prodName, double price, int quantity,
			double totalAmount) {
		super();
		this.prodCode = prodCode;
		this.userID = userID;
		this.prodName = prodName;
		this.price = price;
		this.quantity = quantity;
		this.totalAmount = totalAmount;
	}



	public String getProdCode() {
		return prodCode;
	}


	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}


	public String getUserID() {
		return userID;
	}


	public void setUserID(String userID) {
		this.userID = userID;
	}


	public String getProdName() {
		return prodName;
	}


	public void setProdName(String prodName) {
		this.prodName = prodName;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public double getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	

}
