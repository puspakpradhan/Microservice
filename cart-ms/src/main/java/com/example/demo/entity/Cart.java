package com.example.demo.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CART_TBL")
public class Cart {
	
	@Id  
	@GeneratedValue
	  private int cartId;
	  
	  private int prodId;
	  private int userId;
	  private String userName;
	  private int quantity;
	  private BigDecimal price;
	  private BigDecimal totalAmount;
	  private String prodName;
	  private String prodDesc;
	  
	  
	 public Cart() {
		 
	 } 
	 
	 
	  
	public Cart(int cartId, int prodId, int userId, String userName, int quantity, BigDecimal price,
			BigDecimal totalAmount,String prodName,String prodDesc) {
		super();
		this.cartId = cartId;
		this.prodId = prodId;
		this.userId = userId;
		this.userName = userName;
		this.quantity = quantity;
		this.price = price;
		this.totalAmount = totalAmount;
		this.prodName = prodName;
		this.prodDesc = prodDesc;
	}



	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public int getProdId() {
		return prodId;
	}
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdDesc() {
		return prodDesc;
	}

	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}
	  
	  
	  

}
