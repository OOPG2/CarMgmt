package org.example.CarMgmt.Billing.Payments;

import java.util.HashMap;

import org.example.CarMgmt.Beans.Invoice;

public class InvoiceRetriever extends Retriever<Invoice> {
	public static HashMap<String, Invoice> invoices;
	public InvoiceRetriever() {
		super(Invoice.class);
		invoices = (HashMap<String, Invoice>) init("databases/invoices.csv");
		
		System.out.println(invoices.toString());
	}
}
