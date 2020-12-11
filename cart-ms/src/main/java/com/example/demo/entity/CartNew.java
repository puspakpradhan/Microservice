package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="CARTTBL")
@IdClass(CartNewPrimaryKey.class)
public class CartNew implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String prodCode;
	
	@Id
	private String userID;
	
	private String prodName;
	
	private double price;
	
	private int quantity;
	
	private double totalAmout;
	
	public CartNew() {
		
	}

	public CartNew(String prodCode, String userID, String prodName, double price, int quantity, double totalAmout) {
		super();
		this.prodCode = prodCode;
		this.userID = userID;
		this.prodName = prodName;
		this.price = price;
		this.quantity = quantity;
		this.totalAmout = totalAmout;
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

	public double getTotalAmout() {
		return totalAmout;
	}

	public void setTotalAmout(double totalAmout) {
		this.totalAmout = totalAmout;
	}
	

	
}
