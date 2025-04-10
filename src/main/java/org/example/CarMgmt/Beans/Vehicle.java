package org.example.CarMgmt.Beans;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class Vehicle extends CsvBeans {
    public Vehicle(String vehicle_id, String brand, String model, String type, String car_plate, String daily_rental, String status, String condition, String age, String seats, String mileage) {
        this.vehicle_id = vehicle_id;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.car_plate = car_plate;
        this.daily_rental = daily_rental;
        this.status = status;
        this.condition = condition;
        this.age = age;
        this.seats = seats;
        this.mileage = mileage;
    }

    public Vehicle(){}

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCar_plate() {
        return car_plate;
    }

    public void setCar_plate(String car_plate) {
        this.car_plate = car_plate;
    }

    public String getDaily_rental() {
        return daily_rental;
    }

    public void setDaily_rental(String daily_rental) {
        this.daily_rental = daily_rental;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    @CsvBindByPosition(position = 0)
    private String vehicle_id;

    @CsvBindByPosition(position = 1)
    private String brand;

    @CsvBindByPosition(position = 2)
    private String model;

    @CsvBindByPosition(position = 3)
    private String type;

    @CsvBindByPosition(position = 4)
    private String car_plate;

    @CsvBindByPosition(position = 5)
    private String daily_rental;

    @CsvBindByPosition(position = 6)
    private String status;

    @CsvBindByPosition(position = 7)
    private String condition;

    @CsvBindByPosition(position = 8)
    private String age;

    @CsvBindByPosition(position = 9)
    private String seats;

    @CsvBindByPosition(position = 10)
    private String mileage;

    @Override
    public String getId() {
        return vehicle_id;
    }

    @Override
    public void setId(String id) {
        this.vehicle_id = id;
    }

    public String[] getAll() {
        return new String[]{vehicle_id,brand,model,type,car_plate,daily_rental,status,condition,age,seats,mileage};
    }
}
