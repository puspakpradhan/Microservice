package com.example.demo.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class AuditBean {
	
	private String orderID;
	private String userID;
	private String productName;
	private int quantity;
	private Date orderDate;
	private String Status;
	
	
	
	public AuditBean() {
		
	}

	public AuditBean(String orderID, String userID, String productName, int quantity, Date orderDate, String status) {
		super();
		this.orderID = orderID;
		this.userID = userID;
		this.productName = productName;
		this.quantity = quantity;
		this.orderDate = orderDate;
		Status = status;
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



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	public Date getOrderDate() {
		return orderDate;
	}



	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}



	public String getStatus() {
		return Status;
	}



	public void setStatus(String status) {
		Status = status;
	}
	
	

}
