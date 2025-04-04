package org.example;

import com.opencsv.bean.CsvBindByName;

import java.util.Arrays;
import java.util.List;

public class Vehicle extends CsvBeans{

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

    @CsvBindByName(column = "vehicle_id")
    private String vehicle_id;

    @CsvBindByName(column = "brand")
    private String brand;

    @CsvBindByName(column = "model")
    private String model;

    @CsvBindByName(column = "type")
    private String type;

    @CsvBindByName(column = "car_plate")
    private String car_plate;

    @CsvBindByName(column = "daily_rental")
    private String daily_rental;

    @CsvBindByName(column = "status")
    private String status;

    @CsvBindByName(column = "condition")
    private String condition;

    @CsvBindByName(column = "age")
    private String age;

    @CsvBindByName(column = "seats")
    private String seats;

    @CsvBindByName(column = "mileage")
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
