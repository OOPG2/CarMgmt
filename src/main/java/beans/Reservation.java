/**
 * The Reservation class represents reservation records in the system.
 * Each reservation includes fields for an ID, user ID, vehicle ID, start and end dates, status,
 * daily rental, and insurance details. It uses OpenCSV annotations for CSV serialization.
 */
package beans;

import com.opencsv.bean.CsvBindByName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Reservation provides attributes and functionality for handling reservation data,
 * including methods for date-time manipulation and serialization.
 */
public class Reservation extends CsvBeans {
    /**
     * The unique identifier of the reservation.
     */
    @CsvBindByName(column = "id")
    private int id;

    /**
     * The ID of the user who made the reservation.
     */
    @CsvBindByName(column = "userId")
    private String userId;

    /**
     * The ID of the vehicle being reserved.
     */
    @CsvBindByName(column = "vehicleId")
    private int vehicleId;

    /**
     * The start date and time of the reservation.
     */
    @CsvBindByName(column = "start")
    private String start;

    /**
     * The end date and time of the reservation.
     */
    @CsvBindByName(column = "end")
    private String end;

    /**
     * The current status of the reservation (e.g., Reserved, PickedUp, Returned, Cancelled).
     */
    @CsvBindByName(column = "status")
    private String status;

    /**
     * The daily rental cost for the reservation.
     */
    @CsvBindByName(column = "daily_rental")
    private String daily_rental;

    /**
     * The insurance details for the reservation.
     */
    @CsvBindByName(column = "insurance")
    private String insurance;

    /**
     * Formatter for date-time fields.
     */
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Default constructor for OpenCSV bean binding.
     */
    public Reservation() {
        // No-arg constructor needed for OpenCSV bean binding
    }

    /**
     * Constructs a new Reservation instance with the specified details.
     *
     * @param id           the unique identifier of the reservation
     * @param userId       the ID of the user making the reservation
     * @param vehicleId    the ID of the vehicle being reserved
     * @param start        the start date and time of the reservation
     * @param end          the end date and time of the reservation
     * @param status       the current status of the reservation
     * @param daily_rental the daily rental cost of the reservation
     * @param insurance    the insurance details for the reservation
     */
    public Reservation(int id, String userId, int vehicleId, LocalDateTime start, LocalDateTime end,
                       String status, String daily_rental, String insurance) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.start = DATETIME_FORMAT.format(start);
        this.end = DATETIME_FORMAT.format(end);
        this.status = status;
        this.daily_rental = daily_rental;
        this.insurance = insurance;
    }

    /**
     * Returns the unique identifier of the reservation as a string.
     *
     * @return the reservation ID as a string
     */
    public String getId() {
        return String.valueOf(this.id);
    }

    /**
     * Returns the unique identifier of the reservation as an integer.
     *
     * @return the reservation ID
     */
    public int getReservationID() {
        return this.id;
    }

    /**
     * Sets the unique identifier of the reservation.
     *
     * @param id the reservation ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the ID of the user who made the reservation.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user who made the reservation.
     *
     * @param userId the user ID to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the ID of the vehicle being reserved.
     *
     * @return the vehicle ID
     */
    public int getVehicleId() {
        return vehicleId;
    }

    /**
     * Sets the ID of the vehicle being reserved.
     *
     * @param vehicleId the vehicle ID to set
     */
    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * Returns the start date and time of the reservation.
     *
     * @return the start date and time
     */
    public String getStart() {
        return start;
    }

    /**
     * Sets the start date and time of the reservation.
     *
     * @param start the start date and time to set
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Returns the end date and time of the reservation.
     *
     * @return the end date and time
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets the end date and time of the reservation.
     *
     * @param end the end date and time to set
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Returns the current status of the reservation.
     *
     * @return the reservation status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the reservation.
     *
     * @param status the reservation status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns the daily rental cost of the reservation.
     *
     * @return the daily rental cost
     */
    public String getDailyRental() {
        return daily_rental;
    }

    /**
     * Sets the daily rental cost of the reservation.
     *
     * @param daily_rental the daily rental cost to set
     */
    public void setDailyRental(String daily_rental) {
        this.daily_rental = daily_rental;
    }

    /**
     * Returns the insurance details for the reservation.
     *
     * @return the insurance details
     */
    public String getInsurance() {
        return insurance;
    }

    /**
     * Sets the insurance details for the reservation.
     *
     * @param insurance the insurance details to set
     */
    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    /**
     * Returns the start date-time of the reservation as a LocalDateTime object.
     *
     * @return the start date-time
     */
    public LocalDateTime getStartDateTime() {
        return LocalDate.parse(start, DATETIME_FORMAT).atStartOfDay();
    }

    /**
     * Returns the end date-time of the reservation as a LocalDateTime object.
     *
     * @return the end date-time
     */
    public LocalDateTime getEndDateTime() {
        return LocalDate.parse(end, DATETIME_FORMAT).atTime(23, 59, 59);
    }

    /**
     * Returns a string representation of the Reservation instance.
     *
     * @return a formatted string containing reservation details
     */
    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", vehicleId=" + vehicleId +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    /**
     * Returns all attributes of the reservation as a string array.
     *
     * @return an array of reservation attributes
     */
    public String[] getAll() {
        return new String[]{Integer.toString(id), userId, Integer.toString(vehicleId), status, start, end, daily_rental, insurance};
    }

    /**
     * Returns selected attributes of the reservation as a table row.
     *
     * @return an array of reservation attributes for display in a table row
     */
    public String[] getTableRow() {
        return new String[]{Integer.toString(id), userId, Integer.toString(vehicleId), status, start, end};
    }
}