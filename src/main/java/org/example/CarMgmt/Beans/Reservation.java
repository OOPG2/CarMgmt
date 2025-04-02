package org.example.CarMgmt.Beans;

import com.opencsv.bean.CsvBindByName;

public class Reservation extends CsvBeans {
	@CsvBindByName(column = "reservation_id")
    private String id;
	
	@CsvBindByName(column = "user_id")
    private String user_id;
	
	@CsvBindByName(column = "vehicle_id")
    private String vehicle_id;
	
	@CsvBindByName(column = "status")
    private String status;
	
	@CsvBindByName(column = "start_date")
    private String start_date;
	
	@CsvBindByName(column = "end_date")
    private String end_date;
	
	@CsvBindByName(column = "daily_rental")
    private String daily_rental;
	
	@CsvBindByName(column = "insurance")
    private String insurance;
	
	@CsvBindByName(column = "customer_notes")
    private String customer_notes;
	
	public String getId() {
		return id;
	}
	
	public String getUserId() {
		return user_id;
	}
	
	public String getVehicleId() {
		return vehicle_id;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getStartDate() {
		return start_date;
	}
	
	public String getEndDate() {
		return end_date;
	}
	
	public String getDailyRental() {
		return daily_rental;
	}
	
	public String getInsurance() {
		return insurance;
	}
	
	public String getCustomerNotes() {
		return customer_notes;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setUserId(String user_id) {
		this.user_id = user_id;
	}
	
	public void setVehicleId(String vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setStartDate(String start_date) {
		this.start_date = start_date;
	}
	
	public void setEndDate(String end_date) {
		this.end_date = end_date;
	}
	
	public void setDailyRental(String daily_rental) {
		this.daily_rental = daily_rental;
	}
	
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	
	public void setCustomerNotes(String customer_notes) {
		this.customer_notes = customer_notes;
	}
}
