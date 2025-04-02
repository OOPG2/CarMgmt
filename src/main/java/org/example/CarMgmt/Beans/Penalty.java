package org.example.CarMgmt.Beans;

import com.opencsv.bean.CsvBindByName;

public class Penalty extends CsvBeans {
	@CsvBindByName(column = "penalty_id")
    private String id;

	@CsvBindByName(column = "reservation_id")
    private String reservation_id;

	@CsvBindByName(column = "type")
   private String type;
	
	@CsvBindByName(column = "amount")
    private String amount;
	
	@CsvBindByName(column = "description")
    private String description;
	
	public String getId() {
		return id;
	}
	
	public String getReservationId() {
		return reservation_id;
	}
	
	public String getType() {
		return type;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setReservationId(String reservation_id) {
		this.reservation_id = reservation_id;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
