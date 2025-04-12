package Reservations;

import com.opencsv.bean.CsvBindByName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Model class for a reservation record.
 * Each reservation has an ID, a userId (who made the reservation), a vehicleId, 
 * a start and end date-time, and a status (e.g., Reserved, PickedUp, Returned, Cancelled).
 * Uses OpenCSV annotations for CSV binding of fields.
 */
public class Reservation {
    @CsvBindByName(column = "id")
    private int id;

    @CsvBindByName(column = "userId")
    private String userId;

    @CsvBindByName(column = "vehicleId")
    private int vehicleId;

    @CsvBindByName(column = "start")
    private String start;

    @CsvBindByName(column = "end")
    private String end;

    @CsvBindByName(column = "status")
    private String status;

    @CsvBindByName(column = "daily_rental")
    private String daily_rental;

    @CsvBindByName(column = "insurance")
    private String insurance;

    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Reservation() {
        // No-arg constructor needed for OpenCSV bean binding
    }

    public Reservation(int id, String userId, int vehicleId, LocalDateTime start, LocalDateTime end, String status, String daily_rental, String insurance) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.start = DATETIME_FORMAT.format(start);
        this.end = DATETIME_FORMAT.format(end);
        this.status = status;
        this.daily_rental = daily_rental;
        this.insurance = insurance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDailyRental() {
        return daily_rental;
    }
    public void setDailyRental(String daily_rental) {
        this.daily_rental = daily_rental;
    }

    public String getInsurance() { return insurance;};
    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public LocalDateTime getStartDateTime() {
        return LocalDateTime.parse(start, DATETIME_FORMAT);
    }

    public LocalDateTime getEndDateTime() {
        return LocalDateTime.parse(end, DATETIME_FORMAT);
    }

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
}
