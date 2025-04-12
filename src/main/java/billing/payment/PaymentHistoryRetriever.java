package billing.payment;

import app.Retriever;
import beans.PaymentHistory;

import java.util.Collections;
import java.util.HashMap;

public class PaymentHistoryRetriever extends Retriever<PaymentHistory> {
	public static HashMap<String, PaymentHistory> paymentHistories;
	public static String currentLastRowId = "0";
	public PaymentHistoryRetriever() {
		super(PaymentHistory.class);
		paymentHistories = (HashMap<String, PaymentHistory>) init("databases/payment_histories.csv");
		if (paymentHistories.size() > 0) {
			currentLastRowId = Collections.max(paymentHistories.keySet());
		}
	}
}
