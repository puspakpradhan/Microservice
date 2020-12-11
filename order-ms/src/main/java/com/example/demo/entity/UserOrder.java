package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="USER_ORD")
public class UserOrder {
	
	
	@Id
	private String  orderID;
	
	private String  userID;
	
	public UserOrder() {
		
	}

	public UserOrder(String orderID, String userID) {
		super();
		this.orderID = orderID;
		this.userID = userID;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	

}
