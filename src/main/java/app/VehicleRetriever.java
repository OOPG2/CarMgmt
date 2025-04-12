package app;

import beans.Vehicle;

import java.util.HashMap;

public class VehicleRetriever extends Retriever<Vehicle> {
    public static HashMap<String, Vehicle> vehicles;

    public VehicleRetriever() {
        super(Vehicle.class);
        vehicles = (HashMap<String, Vehicle>) init("databases/vehicles.csv");
    }

    public HashMap<String, Vehicle> getVehicles(){
        return vehicles;
    }

    public Integer getCurrentLastRowId() {
        return vehicles.keySet().stream().mapToInt(Integer::valueOf).max().orElse(0);
    }
}
