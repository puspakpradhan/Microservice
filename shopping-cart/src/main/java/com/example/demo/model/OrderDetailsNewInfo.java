package com.example.demo.model;

public class OrderDetailsNewInfo {
	
		
		
		  private String id;
		  private String orderID;
		  private String productID;
		  private int quanity;
		  private double price;
		  private double amount;
		  
		  
		  public OrderDetailsNewInfo() {
			  
		  }


		public OrderDetailsNewInfo(String id, String orderID, String productID, int quanity, double price,
				double amount) {
			super();
			this.id = id;
			this.orderID = orderID;
			this.productID = productID;
			this.quanity = quanity;
			this.price = price;
			this.amount = amount;
		}


		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
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
