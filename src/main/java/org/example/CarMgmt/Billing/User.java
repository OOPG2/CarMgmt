package org.example.CarMgmt.Billing;

public class User {
	private Integer userId;
	private String type;
	private Integer points;
	private Integer lifetimePoints;
	
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
	
	public Integer getPoints() {
		return points;
	}
	
	public void setPoints(Integer points) {
		this.points = points;
	}
	
	public Integer getLifetimePoints() {
		return lifetimePoints;
	}
	
	public void setLifetimePoints(Integer lifetimePoints) {
		this.lifetimePoints = lifetimePoints;
	}
}
