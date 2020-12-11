package com.example.demo.model;

public class UserOrderInfo {
	
	private String userID;
	private String orderID;
	
	public UserOrderInfo() {
		
	}

	public UserOrderInfo(String userID, String orderID) {
		super();
		this.userID = userID;
		this.orderID = orderID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	} 
	
	
	

}
