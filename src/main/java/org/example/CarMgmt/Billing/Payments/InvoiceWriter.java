package org.example.CarMgmt.Billing.Payments;

import java.io.File;
import java.io.FileWriter;
import java.util.Collections;

import org.example.CarMgmt.Constants;
import org.example.CarMgmt.Beans.Invoice;
import org.example.CarMgmt.Beans.Penalty;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class InvoiceWriter {
	public void writeToCsv(Invoice bean) throws Exception {
		Constants constants = new Constants();
		String filePath = constants.getCsvBasePath() + "databases/invoices.csv";
		File file = new File(filePath);
		boolean fileExists = file.exists();
        try (java.io.Writer writer = new FileWriter(filePath, true)) {
        	StatefulBeanToCsv<Invoice> beanToCsv = new StatefulBeanToCsvBuilder<Invoice>(writer)
                    .withApplyQuotesToAll(false)
                    .withOrderedResults(true)
                    .withQuotechar('\"')
                    .withSeparator(',')
                    .build();
        	/*
        	if (fileExists && file.length() > 0) {
        	    writer.write(System.lineSeparator());
        	}
        	*/

            beanToCsv.write(Collections.singletonList(bean));
        }
    }
}
