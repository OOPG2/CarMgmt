package org.example.CarMgmt.Billing.Payments;

import java.util.Collections;
import java.util.HashMap;

import org.example.CarMgmt.Beans.Invoice;

public class InvoiceRetriever extends Retriever<Invoice> {
	public static HashMap<String, Invoice> invoices;
	public static String currentLastRowId = "0";
	public InvoiceRetriever() {
		super(Invoice.class);
		invoices = (HashMap<String, Invoice>) init("databases/invoices.csv");
		if (invoices.size() > 0) {
			currentLastRowId = Collections.max(invoices.keySet());
		}
	}
}
