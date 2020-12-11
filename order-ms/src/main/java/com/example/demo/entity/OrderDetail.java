package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
 
@Entity
@Table(name = "Order_Details")
public class OrderDetail implements Serializable {
 
    private static final long serialVersionUID = 7550745928843183535L;
 
    @Id
    @Column(name = "ID", length = 50, nullable = false)
    private String id;
    
    
 //My Change   
    @ManyToOne(targetEntity = OrderNew.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID", nullable = false) //
    private OrderNew order;
    
//Orginal below    
 
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ORDER_ID", nullable = false, //
//    foreignKey = @ForeignKey(name = "ORDER_DETAIL_ORD_FK"))
//    private Order order;
    
 //My Change
    @ManyToOne(targetEntity = ProductNew.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_ID", nullable = false) //
    private ProductNew product;
    
//Original below 
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "PRODUCT_ID", nullable = false, //
//            foreignKey = @ForeignKey(name = "ORDER_DETAIL_PROD_FK"))
//    private ProductNew product;
    
    
//    @Column(name ="ORDER_ID")
//    private String orderID;
//    
//    @Column(name ="PRODUCT_ID")
//    private String productID;
//    
//    public String getOrderID() {
//		return orderID;
//	}
////
//	public void setOrderID(String orderID) {
//		this.orderID = orderID;
//	}
////
//	public String getProductID() {
//		return productID;
//	}
//
//	public void setProductID(String productID) {
//		this.productID = productID;
//	}

	
	
	@Column(name = "Quanity", nullable = false)
    private int quanity;
 
    @Column(name = "Price", nullable = false)
    private double price;
 
    @Column(name = "Amount", nullable = false)
    private double amount;
 
    public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    public OrderNew getOrder() {
        return order;
    }
 
    public void setOrder(OrderNew order) {
        this.order = order;
    }
 
    public ProductNew getProduct() {
        return product;
    }
 
    public void setProduct(ProductNew product) {
        this.product = product;
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