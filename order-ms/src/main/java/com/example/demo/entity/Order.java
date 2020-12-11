package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="ORDER_TABLE")
@IdClass(OrderPrimaryKey.class)
public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String userID;
	
	@Id
	private String orderID;
	
	@Id
	private String productID;
	
	private String productName;
	
	private String productDesc;
	
	private int price;
	
	private int quantity;
	
	private String status;
	
	public Order() {
		
	}

	public Order(String userID, String orderID, String productID, String productName, String productDesc, int price,
			int quantity,String status) {
		super();
		this.userID = userID;
		this.orderID = orderID;
		this.productID = productID;
		this.productName = productName;
		this.productDesc = productDesc;
		this.price = price;
		this.quantity = quantity;
		this.status = status;
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

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
