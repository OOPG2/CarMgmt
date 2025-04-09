package org.example.CarMgmt;

public class Constants {

	private final String DEFAULT_PASSWORD = "password";

	public String getDefaultPassword() {
		return DEFAULT_PASSWORD;
	}

	private int daysToDueDate = 7;
	private double overdueFinePerday = 20;
	private double gstRate = 0.09;
	private String UEN = "200604393R";
	private String bankRecipient = "OOP Rentals";
	private String bankName = "OOP Bank";
	private String bankAccountNo = "123-456-7890";
	private String csvBasePath = "/Users/axel/eclipse-workspace/CarMgmt/src/main/resources/";
	
	public int getDaysToDueDate() {
		return daysToDueDate;
	}
	
	public double getOverdueFinePerDay() {
		return overdueFinePerday;
	}
	
	public double getGSTRate() {
		return gstRate;
	}
	
	public String getUEN() {
		return UEN;
	}
	
	public String getBankRecipient() {
		return bankRecipient;
	}
	
	public String getBankName() {
		return bankName;
	}
	
	public String getBankAccountNo() {
		return bankAccountNo;
	}
	
	public String getCsvBasePath() {
		return csvBasePath;
	}
}
