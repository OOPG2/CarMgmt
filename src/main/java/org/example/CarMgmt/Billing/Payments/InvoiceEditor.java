package org.example.CarMgmt.Billing.Payments;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.example.CarMgmt.Constants;
import org.example.CarMgmt.Beans.Invoice;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class InvoiceEditor {
	public static void modifyRowInCsv(String id, Invoice invoice) {
	    try {
	    	Constants constants = new Constants();
	    	String filePath = constants.getCsvBasePath() + "databases/invoices.csv";
	        // Step 1: Read existing data
	        ColumnPositionMappingStrategy<Invoice> strategy = new ColumnPositionMappingStrategy<>();
	        strategy.setType(Invoice.class);

	        new InvoiceRetriever();
	        HashMap<String, Invoice> invoices = InvoiceRetriever.invoices;

	        invoices.put(id, invoice);
	        

	        try (java.io.Writer writer = new FileWriter(filePath, false)) {
	            StatefulBeanToCsv<Invoice> beanToCsv = new StatefulBeanToCsvBuilder<Invoice>(writer)
	                    .withMappingStrategy(strategy)
	                    .withApplyQuotesToAll(false)
	                    .withSeparator(',')
	                    .withOrderedResults(true)
	                    .build();
	            List<Invoice> invoicesList = new ArrayList<Invoice>(invoices.values());
	            beanToCsv.write(invoicesList);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
