package billing.invoice;

import app.App;
import beans.Invoice;
import billing.helper.OverdueFineCalculator;
import com.googlecode.lanterna.gui2.table.Table;
import constants.Constants;
import manager.AuthenticationManager;
import objects.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map.Entry;

public class InvoicesRowPopulator {
	public void populateInvoices() throws Exception {
		App app = new App();
		AuthenticationManager authenticationManager = app.getAuthenticationManager();
		int daysToDueDate = Constants.getDaysToDueDate();
		try {
			new InvoiceRetriever();
	        User user = authenticationManager.getLoggedUser();
	        Table<String> toPayTable = InvoiceSelector.toPayTable;
			Table<String> pastInvoicesTable = InvoiceSelector.pastInvoicesTable;
	        for(Entry<String, Invoice> invoice:InvoiceRetriever.invoices.entrySet()) {
	        	Invoice i = invoice.getValue();
				if (i.getUserId().equals(user.getUserId().toString()) && i.getStatus().toLowerCase().equals("pending")) {
					LocalDate date = LocalDate.parse(i.getCreatedOn(), DateTimeFormatter.BASIC_ISO_DATE);
					LocalDate dueDate = date.plusDays(daysToDueDate);
					LocalDate today = LocalDate.now();
					boolean overdue = dueDate.isBefore(LocalDate.now());
					String formattedDueDate = dueDate.toString();
					Double overdueFine = 0.0;
					Long overdueDays = 0l;
					if (overdue) {
						formattedDueDate += " !OVERDUE!";
						OverdueFineCalculator overdueFineCalculator = new OverdueFineCalculator();
						overdueFine = overdueFineCalculator.calculateOverdueFine(dueDate.format(DateTimeFormatter.BASIC_ISO_DATE), today.format(DateTimeFormatter.BASIC_ISO_DATE));
						overdueDays = overdueFineCalculator.getOverdueDays();
					} 
					i.setTotalOverdueFine(overdueFine);
					i.setOverdueDays(overdueDays);
					Double subtotalInclusiveOverdueFine = Double.parseDouble(i.getSubtotal()) + overdueFine;
					Double gst = subtotalInclusiveOverdueFine * Constants.getGSTRate();
					Double totalAmount = subtotalInclusiveOverdueFine + gst;
					i.setGST(gst);
					i.setTotal(totalAmount);
					toPayTable.getTableModel().addRow(i.getId(), i.getReservationId(), String.format("$%.2f", totalAmount), formattedDueDate);
				} else if (i.getUserId().equals(user.getUserId().toString()) && i.getStatus().toLowerCase().equals("completed")) {
					pastInvoicesTable.getTableModel().addRow(i.getId(), i.getReservationId(), "$" + i.getLockedInAmount());
				}
			}
	    } catch (NullPointerException e) {
	    	e.printStackTrace();
	    }
	}
}
