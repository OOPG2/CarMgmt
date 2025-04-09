package org.example.crms;

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
    private int userId;
    @CsvBindByName(column = "vehicleId")
    private int vehicleId;
    @CsvBindByName(column = "start")
    private String start;   // Date-time as string in format "yyyy-MM-dd HH:mm"
    @CsvBindByName(column = "end")
    private String end;
    @CsvBindByName(column = "status")
    private String status;

    // Date-time format for parsing and formatting date-time strings
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Reservation() {
        // No-arg constructor needed for OpenCSV bean binding
    }

    public Reservation(int id, int userId, int vehicleId, LocalDateTime start, LocalDateTime end, String status) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        // Store date-times as formatted strings for CSV storage
        this.start = DATETIME_FORMAT.format(start);
        this.end = DATETIME_FORMAT.format(end);
        this.status = status;
    }

    // Getters and setters for all fields
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
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

    /** Convenience method to get the start time as a LocalDateTime object. */
    public LocalDateTime getStartDateTime() {
        return LocalDateTime.parse(start, DATETIME_FORMAT);
    }

    /** Convenience method to get the end time as a LocalDateTime object. */
    public LocalDateTime getEndDateTime() {
        return LocalDateTime.parse(end, DATETIME_FORMAT);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", userId=" + userId +
                ", vehicleId=" + vehicleId +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
