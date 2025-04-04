package org.example;

import com.opencsv.bean.CsvBindByName;

public class Reservation extends CsvBeans{
    @Override
    public String getId() {
        return reservation_id;
    }

    @Override
    public void setId(String id) {
        this.reservation_id = id;
    }

    public String[] getAll() {
        return new String[]{reservation_id,vehicle_id,customer_id,status,start_date,end_date,daily_rental,insurance,customer_notes};
    }

    @CsvBindByName(column="reservation_id")
    private String reservation_id;

    @CsvBindByName(column = "vehicle_id")
    private String vehicle_id;

    @CsvBindByName(column="customer_id")
    private String customer_id;

    @CsvBindByName(column="status")
    private String status;

    @CsvBindByName(column="start_date")
    private String start_date;

    @CsvBindByName(column="end_date")
    private String end_date;
    @CsvBindByName(column="daily_rental")
    private String daily_rental;
    @CsvBindByName(column="insurance")
    private String insurance;
    @CsvBindByName(column="customer_notes")
    private String customer_notes;

    public String getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(String reservation_id) {
        this.reservation_id = reservation_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getDaily_rental() {
        return daily_rental;
    }

    public void setDaily_rental(String daily_rental) {
        this.daily_rental = daily_rental;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getCustomer_notes() {
        return customer_notes;
    }

    public void setCustomer_notes(String customer_notes) {
        this.customer_notes = customer_notes;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }
}
