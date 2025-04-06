package org.example.CarMgmt;

public class Constants {
	private int daysToDueDate = 7;
	private double overdueFinePerday = 20;
	private double gstRate = 0.09;
	private String UEN = "200604393R";
	private String bankRecipient = "OOP Rentals";
	private String bankName = "OOP Bank";
	private String bankAccountNo = "123-456-7890";
	
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
}
