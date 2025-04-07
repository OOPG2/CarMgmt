package org.example.CarMgmt.Beans;

import com.opencsv.bean.CsvBindByPosition;

public class PaymentHistories extends CsvBeans {
	@CsvBindByPosition(position = 0)
    private String id;

	@CsvBindByPosition(position = 1)
    private String invoice_id;
	
	@CsvBindByPosition(position = 2)
    private String invoice_amount;
	
	@CsvBindByPosition(position = 3)
    private String payment_method;
	
	@CsvBindByPosition(position = 4)
    private String timestamp;
	
	public String getId() {
		return id;
	}
	
	public String getInvoiceId() {
		return invoice_id;
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
