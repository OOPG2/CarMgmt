package org.example.CarMgmt.Beans;

import com.opencsv.bean.CsvBindByName;

public class Invoice extends CsvBeans {
	@CsvBindByName(column = "invoice_id")
    private String id;

    @CsvBindByName(column = "status")
    private String status;
    
    @CsvBindByName(column = "user_id")
    private String user_id;
    
    @CsvBindByName(column = "reservation_id")
    private String reservation_id;
    
    @CsvBindByName(column = "penalty_ids")
    private String penalty_ids;
    
    @CsvBindByName(column = "total_penalties")
    private String total_penalties;
    
    // excludes overdue fines
    @CsvBindByName(column = "subtotal")
    private String subtotal;
    
    @CsvBindByName(column = "created_on")
    private String created_on;
    
    /* calculated values */
    private Double gst;
    
    private Double total;
    
    private Long overdueDays;
 
    private Double total_overdue_fine;
    
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
    
    public void setSubtotal(String subtotal) {
    	this.subtotal = subtotal;
    }
    
    public void setCreatedOn(String created_on) {
    	this.created_on = created_on;
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
    
    public String getSubtotal() {
    	return subtotal;
    }
    
    public String getCreatedOn() {
    	return created_on;
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
