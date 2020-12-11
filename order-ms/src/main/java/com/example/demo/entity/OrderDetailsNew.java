package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="ORDER_DETAILS_NEW")
public class OrderDetailsNew {
	
	
  @Id
  @Column(name = "ID", length = 50, nullable = false)
    private String id;
	
  @Column(name ="ORDER_ID")
  private String orderID;
  
  @Column(name ="PRODUCT_ID")
  private String productID;
  
  @Column(name = "Quanity", nullable = false)
  private int quanity;

  @Column(name = "Price", nullable = false)
  private double price;

  @Column(name = "Amount", nullable = false)
  private double amount;
  
  
  public OrderDetailsNew() {
	  
  }
  
   public OrderDetailsNew(String id, String orderID, String productID, int quanity, double price, double amount) {
	super();
	this.id = id;
	this.orderID = orderID;
	this.productID = productID;
	this.quanity = quanity;
	this.price = price;
	this.amount = amount;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getQuanity() {
		return quanity;
	}

	public void setQuanity(int quanity) {
		this.quanity = quanity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	
	
 

}
