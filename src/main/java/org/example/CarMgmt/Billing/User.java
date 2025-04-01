package org.example.CarMgmt.Billing;

public class User {
	private Integer userId;
	private String type;
	
	public User(Integer userId, String type) {
		this.setUserId(userId);
		this.setType(type);
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
