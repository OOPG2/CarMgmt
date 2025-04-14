/**
 * The PaymentHistory class represents payment records in the system.
 * It provides attributes for payment details and utilizes OpenCSV for CSV serialization.
 */
package beans;

import com.opencsv.bean.CsvBindByPosition;

/**
 * PaymentHistory encapsulates details about payments, including invoice and user data,
 * payment methods, and timestamps, with fields annotated for CSV binding and serialization.
 */
public class PaymentHistory extends CsvBeans {
	/**
	 * The unique identifier of the payment record.
	 */
	@CsvBindByPosition(position = 0)
	private String id;

	/**
	 * The invoice ID associated with the payment.
	 */
	@CsvBindByPosition(position = 1)
	private String invoice_id;

	/**
	 * The user ID associated with the payment.
	 */
	@CsvBindByPosition(position = 2)
	private String user_id;

	/**
	 * The amount of the invoice associated with the payment.
	 */
	@CsvBindByPosition(position = 3)
	private String invoice_amount;

	/**
	 * The payment method used (e.g., Credit Card, PayPal).
	 */
	@CsvBindByPosition(position = 4)
	private String payment_method;

	/**
	 * The timestamp of the payment.
	 */
	@CsvBindByPosition(position = 5)
	private String timestamp;

	/**
	 * Default constructor for OpenCSV bean binding.
	 */
	public PaymentHistory() {}

	/**
	 * Constructs a new PaymentHistory instance with the specified details.
	 *
	 * @param id             the unique identifier of the payment record
	 * @param invoice_id     the invoice ID associated with the payment
	 * @param user_id        the user ID associated with the payment
	 * @param invoice_amount the amount of the invoice associated with the payment
	 * @param payment_method the payment method used
	 * @param timestamp      the timestamp of the payment
	 */
	public PaymentHistory(String id, String invoice_id, String user_id, String invoice_amount, String payment_method, String timestamp) {
		this.id = id;
		this.invoice_id = invoice_id;
		this.user_id = user_id;
		this.invoice_amount = invoice_amount;
		this.payment_method = payment_method;
		this.timestamp = timestamp;
	}

	/**
	 * Returns the unique identifier of the payment record.
	 *
	 * @return the payment record ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique identifier of the payment record.
	 *
	 * @param id the payment record ID to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the invoice ID associated with the payment.
	 *
	 * @return the invoice ID
	 */
	public String getInvoiceId() {
		return invoice_id;
	}

	/**
	 * Sets the invoice ID associated with the payment.
	 *
	 * @param invoice_id the invoice ID to set
	 */
	public void setInvoiceId(String invoice_id) {
		this.invoice_id = invoice_id;
	}

	/**
	 * Returns the user ID associated with the payment.
	 *
	 * @return the user ID
	 */
	public String getUserId() {
		return user_id;
	}

	/**
	 * Sets the user ID associated with the payment.
	 *
	 * @param user_id the user ID to set
	 */
	public void setUserId(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * Returns the amount of the invoice associated with the payment.
	 *
	 * @return the invoice amount
	 */
	public String getInvoiceAmount() {
		return invoice_amount;
	}

	/**
	 * Sets the amount of the invoice associated with the payment.
	 *
	 * @param invoice_amount the invoice amount to set
	 */
	public void setInvoiceAmount(String invoice_amount) {
		this.invoice_amount = invoice_amount;
	}

	/**
	 * Returns the payment method used.
	 *
	 * @return the payment method
	 */
	public String getPaymentMethod() {
		return payment_method;
	}

	/**
	 * Sets the payment method used.
	 *
	 * @param payment_method the payment method to set
	 */
	public void setPaymentMethod(String payment_method) {
		this.payment_method = payment_method;
	}

	/**
	 * Returns the timestamp of the payment.
	 *
	 * @return the payment timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp of the payment.
	 *
	 * @param timestamp the payment timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
