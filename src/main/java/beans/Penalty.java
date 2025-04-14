/**
 * The Penalty class represents penalties associated with reservations in the system.
 * It provides attributes for penalty details and utilizes OpenCSV for CSV serialization.
 */
package beans;

import com.opencsv.bean.CsvBindByPosition;

/**
 * Penalty provides attributes and functionality for handling penalty data,
 * including ID, reservation ID, amount, and description.
 */
public class Penalty extends CsvBeans {
	/**
	 * The unique identifier for the penalty.
	 */
	@CsvBindByPosition(position = 0)
	private String id;

	/**
	 * The reservation ID associated with the penalty.
	 */
	@CsvBindByPosition(position = 1)
	private String reservation_id;

	/**
	 * The monetary amount of the penalty.
	 */
	@CsvBindByPosition(position = 2)
	private String amount;

	/**
	 * The description of the penalty.
	 */
	@CsvBindByPosition(position = 3)
	private String description;

	/**
	 * Returns the unique identifier for the penalty.
	 *
	 * @return the penalty ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique identifier for the penalty.
	 *
	 * @param id the penalty ID to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the reservation ID associated with the penalty.
	 *
	 * @return the reservation ID
	 */
	public String getReservationId() {
		return reservation_id;
	}

	/**
	 * Sets the reservation ID associated with the penalty.
	 *
	 * @param reservation_id the reservation ID to set
	 */
	public void setReservationId(String reservation_id) {
		this.reservation_id = reservation_id;
	}

	/**
	 * Returns the monetary amount of the penalty.
	 *
	 * @return the penalty amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * Sets the monetary amount of the penalty.
	 *
	 * @param amount the penalty amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * Returns the description of the penalty.
	 *
	 * @return the penalty description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the penalty.
	 *
	 * @param description the penalty description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
