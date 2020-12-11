package com.example.demo.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="AUDIT_TABLE")
@IdClass(AuditNewPrimaryKey.class)
public class AuditNew  implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String auditID;
	
	@Id
	private String userID;
	
	@Id
	private String productCode;
	
	private String productName;
	private double totAmount;
	private double price ;
	private int quantity;
	private String name;
	private String email;
	private String phone;
	private String address;
	private Date orderDate;
	private String status;
	
	
	public AuditNew() {
		
	}
	
	
	
	public AuditNew(String auditID, String userID, String productCode, String productName, double totAmount,
			double price, int quantity, String name, String email, String phone, String address, Date orderDate,String status) {
		super();
		this.auditID = auditID;
		this.userID = userID;
		this.productCode = productCode;
		this.productName = productName;
		this.totAmount = totAmount;
		this.price = price;
		this.quantity = quantity;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.status = status;
		this.orderDate = orderDate;
	}
	public String getAuditID() {
		return auditID;
	}
	public void setAuditID(String auditID) {
		this.auditID = auditID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getTotAmount() {
		return totAmount;
	}
	public void setTotAmount(double totAmount) {
		this.totAmount = totAmount;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}



	public Date getOrderDate() {
		return orderDate;
	}



	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	
	

}
