package org.example.CarMgmt.Beans;

import com.opencsv.bean.CsvBindByName;

public class Invoice {
	@CsvBindByName(column = "invoice_id")
    private String invoice_id;

    @CsvBindByName(column = "status")
    private String status;
    
    @CsvBindByName(column = "user_id")
    private String user_id;
    
    @CsvBindByName(column = "reservation_id")
    private String reservation_id;
    
    @CsvBindByName(column = "penalty_ids")
    private String penalty_ids;
    
    @CsvBindByName(column = "created_on")
    private String created_on;
    
    public void setInvoiceId(String invoice_id) {
    	this.invoice_id = invoice_id;
    }
    
    public void setStatus(String status) {
    	this.status = status;
    }
    
    public void setUserId(String user_id) {
    	this.user_id = user_id;
    }
    
    public void setReservationId(String reservation_id) {
    	this.reservation_id = reservation_id;
    }
    
    public void setPenaltyIds(String penalty_ids) {
    	this.penalty_ids = penalty_ids;
    }
    
    public void setCreatedOn(String created_on) {
    	this.created_on = created_on;
    }
    
    public String getInvoiceId() {
    	return invoice_id;
    }
    
    public String getStatus() {
    	return status;
    }
    
    public String getUserId() {
    	return user_id;
    }
    
    public String getReservationId() {
    	return reservation_id;
    }
    
    public String getPenaltyIds() {
    	return penalty_ids;
    }
    
    public String getCreatedOn() {
    	return created_on;
    }
}
