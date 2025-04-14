/**
 * The Vehicle class represents vehicles available in the reservation system.
 * It provides attributes for vehicle details and uses OpenCSV for CSV serialization.
 */
package Reservations;

import com.opencsv.bean.CsvBindByPosition;

/**
 * Vehicle encapsulates details about a vehicle, including its ID, make, model, type,
 * license plate, rental cost, status, condition, years used, seating capacity, and mileage.
 */
public class Vehicle {

    /**
     * The unique identifier for the vehicle.
     */
    @CsvBindByPosition(position = 0)
    private int id;

    /**
     * The make or brand of the vehicle (e.g., Toyota, Honda).
     */
    @CsvBindByPosition(position = 1)
    private String make;

    /**
     * The model of the vehicle (e.g., Corolla, Civic).
     */
    @CsvBindByPosition(position = 2)
    private String model;

    /**
     * The type of the vehicle (e.g., Sedan, SUV).
     */
    @CsvBindByPosition(position = 3)
    private String type;

    /**
     * The license plate number of the vehicle.
     */
    @CsvBindByPosition(position = 4)
    private String plate;

    /**
     * The rental cost of the vehicle per day.
     */
    @CsvBindByPosition(position = 5)
    private double rentalCost;

    /**
     * The current status of the vehicle (e.g., Available, Rented).
     */
    @CsvBindByPosition(position = 6)
    private String status;

    /**
     * The condition of the vehicle (e.g., New, Good, Needs Maintenance).
     */
    @CsvBindByPosition(position = 7)
    private String condition;

    /**
     * The number of years the vehicle has been in use.
     */
    @CsvBindByPosition(position = 8)
    private int yearsUsed;

    /**
     * The seating capacity of the vehicle.
     */
    @CsvBindByPosition(position = 9)
    private int seats;

    /**
     * The mileage of the vehicle in kilometers.
     */
    @CsvBindByPosition(position = 10)
    private int mileage;

    /**
     * Default constructor for OpenCSV bean binding.
     */
    public Vehicle() {}

    /**
     * Constructs a new Vehicle instance with the specified details.
     *
     * @param id         the unique identifier of the vehicle
     * @param make       the brand of the vehicle
     * @param model      the model of the vehicle
     * @param type       the type of the vehicle
     * @param plate      the license plate number of the vehicle
     * @param rentalCost the rental cost per day for the vehicle
     * @param status     the current status of the vehicle
     * @param condition  the condition of the vehicle
     * @param yearsUsed  the number of years the vehicle has been in use
     * @param seats      the seating capacity of the vehicle
     * @param mileage    the mileage of the vehicle in kilometers
     */
    public Vehicle(int id, String make, String model, String type, String plate, double rentalCost,
                   String status, String condition, int yearsUsed, int seats, int mileage) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.type = type;
        this.plate = plate;
        this.rentalCost = rentalCost;
        this.status = status;
        this.condition = condition;
        this.yearsUsed = yearsUsed;
        this.seats = seats;
        this.mileage = mileage;
    }

    /**
     * Returns the unique identifier for the vehicle.
     *
     * @return the vehicle ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the vehicle.
     *
     * @param id the vehicle ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the make or brand of the vehicle.
     *
     * @return the vehicle make
     */
    public String getMake() {
        return make;
    }

    /**
     * Sets the make or brand of the vehicle.
     *
     * @param make the vehicle make to set
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * Returns the model of the vehicle.
     *
     * @return the vehicle model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the vehicle.
     *
     * @param model the vehicle model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Returns the type of the vehicle.
     *
     * @return the vehicle type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the vehicle.
     *
     * @param type the vehicle type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the license plate number of the vehicle.
     *
     * @return the license plate number
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Sets the license plate number of the vehicle.
     *
     * @param plate the license plate number to set
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * Returns the rental cost of the vehicle per day.
     *
     * @return the rental cost per day
     */
    public double getRentalCost() {
        return rentalCost;
    }

    /**
     * Sets the rental cost of the vehicle per day.
     *
     * @param rentalCost the rental cost per day to set
     */
    public void setRentalCost(double rentalCost) {
        this.rentalCost = rentalCost;
    }

    /**
     * Returns the current status of the vehicle.
     *
     * @return the vehicle status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the vehicle.
     *
     * @param status the vehicle status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns the condition of the vehicle.
     *
     * @return the vehicle condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Sets the condition of the vehicle.
     *
     * @param condition the vehicle condition to set
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Returns the number of years the vehicle has been in use.
     *
     * @return the years used
     */
    public int getYearsUsed() {
        return yearsUsed;
    }

    /**
     * Sets the number of years the vehicle has been in use.
     *
     * @param yearsUsed the years used to set
     */
    public void setYearsUsed(int yearsUsed) {
        this.yearsUsed = yearsUsed;
    }

    /**
     * Returns the seating capacity of the vehicle.
     *
     * @return the number of seats
     */
    public int getSeats() {
        return seats;
    }

    /**
     * Sets the seating capacity of the vehicle.
     *
     * @param seats the number of seats to set
     */
    public void setSeats(int seats) {
        this.seats = seats;
    }

    /**
     * Returns the mileage of the vehicle in kilometers.
     *
     * @return the mileage in kilometers
     */
    public int getMileage() {
        return mileage;
    }

    /**
     * Sets the mileage of the vehicle in kilometers.
     *
     * @param mileage the mileage to set
     */
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    /**
     * Returns a string representation of the Vehicle instance.
     *
     * @return a formatted string containing vehicle details
     */
    @Override
    public String toString() {
        return String.format(
                "Vehicle ID: %d | %s %s (%s) | Plate: %s | $%.2f/day | Status: %s | Condition: %s | %d yrs | %d seats | %d km",
                id, make, model, type, plate, rentalCost, status, condition, yearsUsed, seats, mileage
        );
    }
}
