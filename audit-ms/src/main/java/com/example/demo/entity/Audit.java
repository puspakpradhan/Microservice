package com.example.demo.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AUDIT_TBL")
public class Audit {
	
	
	@Id
	private String orderID;
	
	private String userID;
	private String productName;
	private int quantity;
	private Date orderDate;
	private String status;
	
	public Audit() {
		
	}
	public Audit(String orderID, String userID, String productName, int quantity, Date orderDate, String status) {
		super();
		this.orderID = orderID;
		this.userID = userID;
		this.productName = productName;
		this.quantity = quantity;
		this.orderDate = orderDate;
		this.status = status;
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
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	

}
