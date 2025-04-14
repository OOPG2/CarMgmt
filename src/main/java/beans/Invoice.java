/**
 * The Invoice class represents invoices in the system.
 * It provides attributes for invoice details and utilizes OpenCSV for CSV serialization.
 */
package beans;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvIgnore;

/**
 * Invoice provides functionality for handling invoice data, including penalties, amounts, dates,
 * and calculated values, with fields annotated for CSV binding and serialization.
 */
public class Invoice extends CsvBeans {
    /**
     * The unique identifier of the invoice.
     */
    @CsvBindByPosition(position = 0)
    private String id;

    /**
     * The status of the invoice (e.g., Paid, Pending).
     */
    @CsvBindByPosition(position = 1)
    private String status;

    /**
     * The user ID associated with the invoice.
     */
    @CsvBindByPosition(position = 2)
    private String user_id;

    /**
     * The reservation ID linked to the invoice.
     */
    @CsvBindByPosition(position = 3)
    private String reservation_id;

    /**
     * The penalty IDs linked to the invoice.
     */
    @CsvBindByPosition(position = 4)
    private String penalty_ids;

    /**
     * The total penalties incurred in the invoice.
     */
    @CsvBindByPosition(position = 5)
    private String total_penalties;

    /**
     * The redeemed dollar amount applied to the invoice.
     */
    @CsvBindByPosition(position = 6)
    private String redeemed_dollar_amount;

    /**
     * The base amount of the invoice (rental + insurance - discounts).
     */
    @CsvBindByPosition(position = 7)
    private String base_amount;

    /**
     * The subtotal of the invoice, excluding overdue fines.
     */
    @CsvBindByPosition(position = 8)
    private String subtotal;

    /**
     * The date the invoice was created.
     */
    @CsvBindByPosition(position = 9)
    private String created_on;

    /**
     * The locked-in amount for the invoice.
     */
    @CsvBindByPosition(position = 10)
    private String locked_in_amount;

    /**
     * The locked-in date for the invoice.
     */
    @CsvBindByPosition(position = 11)
    private String locked_in_date;

    /**
     * The GST calculated for the invoice (ignored for CSV serialization).
     */
    @CsvIgnore
    private Double gst;

    /**
     * The total amount calculated for the invoice (ignored for CSV serialization).
     */
    @CsvIgnore
    private Double total;

    /**
     * The number of overdue days for the invoice.
     */
    @CsvBindByPosition(position = 12)
    private Long overdueDays;

    /**
     * The total overdue fine calculated for the invoice.
     */
    @CsvBindByPosition(position = 13)
    private Double total_overdue_fine;

    /**
     * Default constructor for creating an Invoice instance.
     */
    public Invoice() {
    }

    /**
     * Constructs a new Invoice instance with the specified details.
     *
     * @param id                     the unique identifier of the invoice
     * @param status                 the status of the invoice
     * @param user_id                the user ID associated with the invoice
     * @param reservation_id         the reservation ID linked to the invoice
     * @param penalty_ids            the penalty IDs linked to the invoice
     * @param total_penalties        the total penalties incurred
     * @param redeemed_dollar_amount the redeemed dollar amount applied
     * @param base_amount            the base amount of the invoice
     * @param subtotal               the subtotal of the invoice
     * @param created_on             the creation date of the invoice
     */
    public Invoice(String id, String status, String user_id, String reservation_id, String penalty_ids,
                   String total_penalties, String redeemed_dollar_amount, String base_amount, String subtotal,
                   String created_on) {
        this.id = id;
        this.status = status;
        this.user_id = user_id;
        this.reservation_id = reservation_id;
        this.penalty_ids = penalty_ids;
        this.total_penalties = total_penalties;
        this.redeemed_dollar_amount = redeemed_dollar_amount;
        this.base_amount = base_amount;
        this.subtotal = subtotal;
        this.created_on = created_on;
    }

    /**
     * Sets the unique identifier of the invoice.
     *
     * @param id the invoice ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the status of the invoice.
     *
     * @param status the invoice status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Sets the user ID associated with the invoice.
     *
     * @param user_id the user ID to set
     */
    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    /**
     * Sets the reservation ID linked to the invoice.
     *
     * @param reservation_id the reservation ID to set
     */
    public void setReservationId(String reservation_id) {
        this.reservation_id = reservation_id;
    }

    /**
     * Sets the penalty IDs linked to the invoice.
     *
     * @param penalty_ids the penalty IDs to set
     */
    public void setPenaltyIds(String penalty_ids) {
        this.penalty_ids = penalty_ids;
    }

    /**
     * Sets the total penalties incurred in the invoice.
     *
     * @param total_penalties the total penalties to set
     */
    public void setTotalPenalties(String total_penalties) {
        this.total_penalties = total_penalties;
    }

    /**
     * Sets the redeemed dollar amount applied to the invoice.
     *
     * @param redeemed_dollar_amount the redeemed dollar amount to set
     */
    public void setRedeemedDollarAmount(String redeemed_dollar_amount) {
        this.redeemed_dollar_amount = redeemed_dollar_amount;
    }

    /**
     * Sets the base amount of the invoice.
     *
     * @param base_amount the base amount to set
     */
    public void setBaseAmount(String base_amount) {
        this.base_amount = base_amount;
    }

    /**
     * Sets the subtotal of the invoice.
     *
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Sets the creation date of the invoice.
     *
     * @param created_on the creation date to set
     */
    public void setCreatedOn(String created_on) {
        this.created_on = created_on;
    }

    /**
     * Sets the locked-in amount for the invoice.
     *
     * @param locked_in_amount the locked-in amount to set
     */
    public void setLockedInAmount(String locked_in_amount) {
        this.locked_in_amount = locked_in_amount;
    }

    /**
     * Sets the locked-in date for the invoice.
     *
     * @param locked_in_date the locked-in date to set
     */
    public void setLockedInDate(String locked_in_date) {
        this.locked_in_date = locked_in_date;
    }

    /**
     * Sets the GST calculated for the invoice.
     *
     * @param gst the GST to set
     */
    public void setGST(Double gst) {
        this.gst = gst;
    }

    /**
     * Sets the total amount calculated for the invoice.
     *
     * @param total the total amount to set
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * Sets the number of overdue days for the invoice.
     *
     * @param overdueDays the number of overdue days to set
     */
    public void setOverdueDays(Long overdueDays) {
        this.overdueDays = overdueDays;
    }

    /**
     * Sets the total overdue fine for the invoice.
     *
     * @param total_overdue_fine the total overdue fine to set
     */
    public void setTotalOverdueFine(Double total_overdue_fine) {
        this.total_overdue_fine = total_overdue_fine;
    }

    /**
     * Returns the unique identifier of the invoice.
     *
     * @return the invoice ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the status of the invoice.
     *
     * @return the invoice status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns the user ID associated with the invoice.
     *
     * @return the user ID
     */
    public String getUserId() {
        return user_id;
    }

    /**
     * Returns the reservation ID linked to the invoice.
     *
     * @return the reservation ID
     */
    public String getReservationId() {
        return reservation_id;
    }

    /**
     * Returns the penalty IDs linked to the invoice.
     *
     * @return the penalty IDs
     */
    public String getPenaltyIds() {
        return penalty_ids;
    }

    /**
     * Returns the total penalties incurred in the invoice.
     *
     * @return the total penalties
     */
    public String getTotalPenalties() {
        return total_penalties;
    }

    /**
     * Returns the redeemed dollar amount applied to the invoice.
     *
     * @return the redeemed dollar amount
     */
    public String getRedeemedDollarAmount() {
        return redeemed_dollar_amount;
    }

    /**
     * Returns the base amount of the invoice.
     *
     * @return the base amount
     */
    public String getBaseAmount() {
        return base_amount;
    }

    /**
     * Returns the subtotal of the invoice.
     *
     * @return the subtotal
     */
    public String getSubtotal() {
        return subtotal;
    }

    /**
     * Returns the creation date of the invoice.
     *
     * @return the creation date
     */
    public String getCreatedOn() {
        return created_on;
    }

    /**
     * Returns the locked-in amount for the invoice.
     *
     * @return the locked-in amount
     */
    public String getLockedInAmount() {
        return locked_in_amount;
    }

    /**
     * Returns the locked-in date for the invoice.
     *
     * @return the locked-in date
     */
    public String getLockedInDate() {
        return locked_in_date;
    }

    /**
     * Returns the GST calculated for the invoice.
     *
     * @return the GST
     */
    public Double getGST() {
        return gst;
    }

    /**
     * Returns the total amount calculated for the invoice.
     *
     * @return the total amount
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Returns the number of overdue days for the invoice.
     *
     * @return the number of overdue days
     */
    public Long getOverdueDays() {
        return overdueDays;
    }

    /**
     * Returns the total overdue fine for the invoice.
     *
     * @return the total overdue fine
     */
    public Double getTotalOverdueFine() {
        return total_overdue_fine;
    }
}