package org.example.CarMgmt.Billing.Payments;

import java.io.File;
import java.io.FileWriter;
import java.util.Collections;

import org.example.CarMgmt.Constants;
import org.example.CarMgmt.Beans.Invoice;
import org.example.CarMgmt.Beans.PaymentHistory;
import org.example.CarMgmt.Beans.Penalty;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class PaymentHistoryWriter {
	public void writeToCsv(PaymentHistory bean) throws Exception {
		Constants constants = new Constants();
		String filePath = constants.getCsvBasePath() + "databases/payment_histories.csv";
		File file = new File(filePath);
		boolean fileExists = file.exists();
        try (java.io.Writer writer = new FileWriter(filePath, true)) {
        	StatefulBeanToCsv<PaymentHistory> beanToCsv = new StatefulBeanToCsvBuilder<PaymentHistory>(writer)
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
