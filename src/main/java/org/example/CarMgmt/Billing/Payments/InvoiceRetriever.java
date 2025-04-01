package org.example.CarMgmt.Billing.Payments;

import org.example.CarMgmt.Billing.User;
import org.example.CarMgmt.Billing.UserSelection;

import com.googlecode.lanterna.gui2.table.Table;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.example.CarMgmt.Beans.*;

public class InvoiceRetriever {
	public void populateInvoices() throws Exception {
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("databases/invoices.csv");
			InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(inputStream));
	    	Class<Invoice> invoice = Invoice.class;
	        CsvToBean<Invoice> cb = new CsvToBeanBuilder<Invoice>(reader)
	        		.withIgnoreLeadingWhiteSpace(true)
	        		.withType(invoice)
	        		.build();
	        List<Invoice> invoices = cb.parse();
	        User user = UserSelection.user;
	        Table<String> table = InvoiceSelector.table;
	        for(Invoice i: invoices) {
				if (i.getUserId().equals(user.getUserId().toString()) && i.getStatus().toLowerCase().equals("pending")) {
					LocalDate date = LocalDate.parse(i.getCreatedOn(), DateTimeFormatter.BASIC_ISO_DATE);
					LocalDate dueDate = date.plusDays(7);
					String formattedDueDate = dueDate.toString() + (dueDate.isBefore(LocalDate.now()) ? " !OVERDUE!" : "");
					table.getTableModel().addRow(i.getInvoiceId(), i.getReservationId(), "x", formattedDueDate);
				}
			}
	    } catch (NullPointerException e) {
	    	e.printStackTrace();
	    }
	}
}
