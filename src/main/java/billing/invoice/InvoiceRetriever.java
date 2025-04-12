package billing.invoice;

import app.Retriever;
import beans.Invoice;

import java.util.Collections;
import java.util.HashMap;

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
