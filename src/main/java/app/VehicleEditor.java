package app;

import beans.Vehicle;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import constants.Constants;

import java.io.FileWriter;
import java.util.HashMap;

public class VehicleEditor {
    public static void modifyRowInCsv(String id, Vehicle vehicle) {
        try {
            Constants constants = new Constants();
            String filePath = constants.getCsvBasePath() + "databases/vehicles.csv";
            // Step 1: Read existing data
            ColumnPositionMappingStrategy<Vehicle> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Vehicle.class);

            new VehicleRetriever();
            HashMap<String, Vehicle> vehicles = VehicleRetriever.vehicles;

            vehicles.put(id, vehicle);


            try (java.io.Writer writer = new FileWriter(filePath, false)) {
                StatefulBeanToCsv<Vehicle> beanToCsv = new StatefulBeanToCsvBuilder<Vehicle>(writer)
                        .withMappingStrategy(strategy)
                        .withApplyQuotesToAll(false)
                        .withSeparator(',')
                        .withOrderedResults(true)
                        .build();
                beanToCsv.write(vehicles.keySet().stream()
                        .mapToInt(Integer::valueOf)
                        .sorted()
                        .mapToObj(k-> vehicles.get(String.valueOf(k)))
                        .toList());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteRowInCSV(String id){
        try {
            Constants constants = new Constants();
            String filePath = constants.getCsvBasePath() + "databases/vehicles.csv";
            // Step 1: Read existing data
            ColumnPositionMappingStrategy<Vehicle> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Vehicle.class);

            new VehicleRetriever();
            HashMap<String, Vehicle> vehicles = VehicleRetriever.vehicles;

            vehicles.remove(id);


            try (java.io.Writer writer = new FileWriter(filePath, false)) {
                StatefulBeanToCsv<Vehicle> beanToCsv = new StatefulBeanToCsvBuilder<Vehicle>(writer)
                        .withMappingStrategy(strategy)
                        .withApplyQuotesToAll(false)
                        .withSeparator(',')
                        .withOrderedResults(true)
                        .build();
                beanToCsv.write(vehicles.keySet().stream()
                        .mapToInt(Integer::valueOf)
                        .sorted()
                        .mapToObj(k-> vehicles.get(String.valueOf(k)))
                        .toList());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
