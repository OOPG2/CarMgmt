package org.example.CarMgmt.Beans;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvIgnore;

public class Invoice extends CsvBeans {
	//@CsvBindByName(column = "invoice_id")
	@CsvBindByPosition(position = 0)
    private String id;

    //@CsvBindByName(column = "status")
	@CsvBindByPosition(position = 1)
    private String status;
    
    //@CsvBindByName(column = "user_id")
	@CsvBindByPosition(position = 2)
    private String user_id;
    
    //@CsvBindByName(column = "reservation_id")
	@CsvBindByPosition(position = 3)
    private String reservation_id;
    
    //@CsvBindByName(column = "penalty_ids")
	@CsvBindByPosition(position = 4)
    private String penalty_ids;
    
    //@CsvBindByName(column = "total_penalties")
	@CsvBindByPosition(position = 5)
    private String total_penalties;
	
	@CsvBindByPosition(position = 6)
    private String redeemed_dollar_amount;
    
    // excludes overdue fines
    //@CsvBindByName(column = "subtotal")
	@CsvBindByPosition(position = 7)
    private String subtotal;
    
    //@CsvBindByName(column = "created_on")
	@CsvBindByPosition(position = 8)
    private String created_on;
	
	@CsvBindByPosition(position = 9)
    private String locked_in_amount;
	
	@CsvBindByPosition(position = 10)
    private String locked_in_date;
    
    /* calculated values */
	@CsvIgnore
    private Double gst;
    
	@CsvIgnore
    private Double total;
    
	@CsvIgnore
    private Long overdueDays;
 
	@CsvIgnore
    private Double total_overdue_fine;
	
	public Invoice() {}
    
    public Invoice(String id, String status, String user_id, String reservation_id, String penalty_ids, String total_penalties, String redeemed_dollar_amount, String subtotal, String created_on) {
    	this.id = id;
    	this.status = status;
    	this.user_id = user_id;
    	this.reservation_id = reservation_id;
    	this.penalty_ids = penalty_ids;
    	this.total_penalties = total_penalties;
    	this.redeemed_dollar_amount = redeemed_dollar_amount;
    	this.subtotal = subtotal;
    	this.created_on = created_on;
    }
    
    public void setId(String id) {
    	this.id = id;
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
    
    public void setTotalPenalties(String total_penalties) {
    	this.total_penalties = total_penalties;
    }
    
    public void setRedeemedDollarAmount(String redeemed_dollar_amount) {
    	this.redeemed_dollar_amount = redeemed_dollar_amount;
    }
    
    public void setSubtotal(String subtotal) {
    	this.subtotal = subtotal;
    }
    
    public void setCreatedOn(String created_on) {
    	this.created_on = created_on;
    }
    
    public void setLockedInAmount(String locked_in_amount) {
    	this.locked_in_amount = locked_in_amount;
    }
    
    public void setLockedInDate(String locked_in_date) {
    	this.locked_in_date = locked_in_date;
    }
    
    public void setGST(Double gst) {
    	this.gst = gst;
    }
    
    public void setTotal(Double total) {
    	this.total = total;
    }
    
    public void setOverdueDays(Long overdueDays) {
    	this.overdueDays = overdueDays;
    }

    public void setTotalOverdueFine(Double total_overdue_fine) {
    	this.total_overdue_fine = total_overdue_fine;
    }
    
    public String getId() {
    	return id;
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
    
    public String getTotalPenalties() {
    	return total_penalties;
    }
    
    public String getRedeemedDollarAmount() {
    	return redeemed_dollar_amount;
    }
    
    public String getSubtotal() {
    	return subtotal;
    }
    
    public String getCreatedOn() {
    	return created_on;
    }
    
    public String getLockedInAmount() {
    	return locked_in_amount;
    }
    
    public String getLockedInDate() {
    	return locked_in_date;
    }
    
    public Double getGST() {
    	return gst;
    }
    
    public Double getTotal() {
    	return total;
    }
    
    public Long getOverdueDays() {
    	return overdueDays;
    }

    public Double getTotalOverdueFine() {
    	return total_overdue_fine;
    }
}
