package beans;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class Reservation extends CsvBeans {
    @Override
    public String getId() {
        return reservation_id;
    }

    @Override
    public void setId(String id) {
        this.reservation_id = id;
    }

    public String[] getAll() {
        return new String[]{reservation_id,customer_id,vehicle_id,status,start_date,end_date,daily_rental,insurance,customer_notes};
    }

    @CsvBindByPosition(position = 0)
    private String reservation_id;

    @CsvBindByPosition(position = 1)
    private String customer_id;
    @CsvBindByPosition(position = 2)
    private String vehicle_id;


    @CsvBindByPosition(position = 3)
    private String status;

    @CsvBindByPosition(position = 4)
    private String start_date;

    @CsvBindByPosition(position = 5)
    private String end_date;
    @CsvBindByPosition(position = 6)
    private String daily_rental;
    @CsvBindByPosition(position = 7)
    private String insurance;
    @CsvBindByPosition(position = 8)
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
