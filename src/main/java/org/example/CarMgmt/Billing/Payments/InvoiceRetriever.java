package org.example.CarMgmt.Billing.Payments;

import java.util.HashMap;

import org.example.CarMgmt.Beans.CsvBeans;
import org.example.CarMgmt.Beans.Invoice;
import org.example.CarMgmt.CsvParser.CsvParser;

public class InvoiceRetriever extends Retriever<Invoice> {
	
	public InvoiceRetriever() {
		super(Invoice.class);
		init("databases/invoices.csv");
		//init();
	}
	
	/*
	public void init() {
		try {
			CsvParser csvParser = new CsvParser();
			hashmap = csvParser.csvToHashmap(Invoice.class, "databases/invoices.csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
	/*
	public Invoice retrieveReservationById(String id) {
		return (Invoice)hashmap.get(id);
	}
	*/
}
