package org.example.CarMgmt.Beans;

import com.opencsv.bean.CsvBindByPosition;

public class PaymentHistory extends CsvBeans {
	@CsvBindByPosition(position = 0)
    private String id;

	@CsvBindByPosition(position = 1)
    private String invoice_id;
	
	@CsvBindByPosition(position = 2)
    private String user_id;
	
	@CsvBindByPosition(position = 3)
    private String invoice_amount;
	
	@CsvBindByPosition(position = 4)
    private String payment_method;
	
	@CsvBindByPosition(position = 5)
    private String timestamp;
	
	public PaymentHistory() {}
	
	public PaymentHistory(String id, String invoice_id, String user_id, String invoice_amount, String payment_method, String timestamp) {
		this.id = id;
		this.invoice_id = invoice_id;
		this.user_id = user_id;
		this.invoice_amount = invoice_amount;
		this.payment_method = payment_method;
		this.timestamp = timestamp;
	}
	
	public String getId() {
		return id;
	}
	
	public String getInvoiceId() {
		return invoice_id;
	}
	
	public String getUserId() {
		return user_id;
	}
	
	public String getInvoiceAmount() {
		return invoice_amount;
	}
	
	public String getPaymentMethod() {
		return payment_method;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setInvoiceId(String invoice_id) {
		this.invoice_id = invoice_id;
	}
	
	public void setUserId(String user_id) {
		this.user_id = user_id;
	}
	
	public void setInvoiceAmount(String invoice_amount) {
		this.invoice_amount = invoice_amount;
	}
	
	public void setPaymentMethod(String payment_method) {
		this.payment_method = payment_method;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
