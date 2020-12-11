package com.example.demo.dto;

import lombok.Data;

@Data
public class UserTockenBean {
	
	private String tocken;
	private String userID;
	private String userName;
	
	public UserTockenBean() {
		
	}
	
	public UserTockenBean(String tocken, String userID, String userName) {
		super();
		this.tocken = tocken;
		this.userID = userID;
		this.userName = userName;
	}
	public String getTocken() {
		return tocken;
	}
	public void setTocken(String tocken) {
		this.tocken = tocken;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	


}
