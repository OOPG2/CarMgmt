package billing.invoice;

import constants.*;
import beans.*;

import java.io.FileWriter;
import java.util.Collections;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class InvoiceWriter {
	public void writeToCsv(Invoice bean) throws Exception {
		String filePath = Constants.getCsvBasePath() + "databases/invoices.csv";
		//File file = new File(filePath);
		//boolean fileExists = file.exists();
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
