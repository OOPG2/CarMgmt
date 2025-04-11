package billing.payment;

import constants.*;
import beans.*;

import java.io.FileWriter;
import java.util.Collections;


import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class PaymentHistoryWriter {
	public void writeToCsv(PaymentHistory bean) throws Exception {
		String filePath = Constants.getCsvBasePath() + "databases/payment_histories.csv";
		//File file = new File(filePath);
		//boolean fileExists = file.exists();
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
