package org.example.crms;

import com.opencsv.bean.CsvBindByName;

/**
 * Model class for a vehicle that can be reserved.
 * Each vehicle has an ID, make, model, year, and type (category).
 * Uses OpenCSV annotations for CSV binding.
 */
public class Vehicle {
    @CsvBindByName(column = "id")
    private int id;
    @CsvBindByName(column = "make")
    private String make;
    @CsvBindByName(column = "model")
    private String model;
    @CsvBindByName(column = "year")
    private int year;
    @CsvBindByName(column = "type")
    private String type;

    public Vehicle() {
        // No-arg constructor for OpenCSV
    }

    public Vehicle(int id, String make, String model, int year, String type) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.type = type;
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }
    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", type='" + type + '\'' +
                '}';
    }
}
