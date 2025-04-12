package beans;



import com.opencsv.bean.CsvBindByPosition;

public class Vehicle extends CsvBeans{

    @CsvBindByPosition(position = 0)
    private int id;

    @CsvBindByPosition(position = 1)
    private String make;

    @CsvBindByPosition(position = 2)
    private String model;

    @CsvBindByPosition(position = 3)
    private String type;

    @CsvBindByPosition(position = 4)
    private String plate;

    @CsvBindByPosition(position = 5)
    private double rentalCost;

    @CsvBindByPosition(position = 6)
    private String status;

    @CsvBindByPosition(position = 7)
    private String condition;

    @CsvBindByPosition(position = 8)
    private int yearsUsed;

    @CsvBindByPosition(position = 9)
    private int seats;

    @CsvBindByPosition(position = 10)
    private int mileage;

    public Vehicle() {}

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

    // Getters and Setters

    @Override
    public String getId() { return Integer.toString(id); }

    public int getVehicleID() {return this.id;}
    public void setId(int id) { this.id = id; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }

    public double getRentalCost() { return rentalCost; }
    public void setRentalCost(double rentalCost) { this.rentalCost = rentalCost; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public int getYearsUsed() { return yearsUsed; }
    public void setYearsUsed(int yearsUsed) { this.yearsUsed = yearsUsed; }

    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }

    public int getMileage() { return mileage; }
    public void setMileage(int mileage) { this.mileage = mileage; }

    @Override
    public String toString() {
        return String.format(
                "Vehicle ID: %d | %s %s (%s) | Plate: %s | $%.2f/day | Status: %s | Condition: %s | %d yrs | %d seats | %d km",
                id, make, model, type, plate, rentalCost, status, condition, yearsUsed, seats, mileage
        );
    }

    public String[] getAll() {
        return new String[]{String.valueOf(id),make,model,type,plate, String.valueOf(rentalCost),status,condition, String.valueOf(yearsUsed), String.valueOf(seats), String.valueOf(mileage)};
    }
}

