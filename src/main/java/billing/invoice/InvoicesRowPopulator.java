package billing.invoice;

import billing.helper.*;
import constants.*;
import objects.*;
import beans.*;

import com.googlecode.lanterna.gui2.table.Table;

import app.App;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map.Entry;

public class InvoicesRowPopulator {
	public void populateInvoices() throws Exception {
		int daysToDueDate = Constants.getDaysToDueDate();
		try {
			new InvoiceRetriever();
	        User user = App.authenticationManager.getLoggedUser();
	        Table<String> table = InvoiceSelector.table;
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
					table.getTableModel().addRow(i.getId(), i.getReservationId(), String.format("$%.2f", totalAmount), formattedDueDate);
				}
			}
	    } catch (NullPointerException e) {
	    	e.printStackTrace();
	    }
	}
}
