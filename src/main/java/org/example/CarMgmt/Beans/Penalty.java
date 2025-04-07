package org.example.CarMgmt.Beans;

import com.opencsv.bean.CsvBindByPosition;

public class Penalty extends CsvBeans {
	//@CsvBindByName(column = "penalty_id")
	@CsvBindByPosition(position = 0)
    private String id;

	//@CsvBindByName(column = "reservation_id")
	@CsvBindByPosition(position = 1)
    private String reservation_id;
	
	//@CsvBindByName(column = "amount")
	@CsvBindByPosition(position = 2)
    private String amount;
	
	//@CsvBindByName(column = "description")
	@CsvBindByPosition(position = 3)
    private String description;
	
	public String getId() {
		return id;
	}
	
	public String getReservationId() {
		return reservation_id;
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
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
