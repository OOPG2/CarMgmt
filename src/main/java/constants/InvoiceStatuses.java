package constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvoiceStatuses {
	public List<String> invoiceStatuses = new ArrayList<String>();
	public List<String> getInvoiceStatuses() {
		invoiceStatuses.add("Pending");
		invoiceStatuses.add("Completed");
		invoiceStatuses.add("Voided");
		return invoiceStatuses;
	}
}
