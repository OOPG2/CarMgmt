package app;

import beans.Reservation;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import constants.Constants;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ReservationEditor {
    public static void modifyRowInCsv(String id, Reservation reservation) {
        Constants constants = new Constants();
        String filePath = constants.getCsvBasePath() + "databases/reservations.csv";
        // Step 1: Read existing data
        ColumnPositionMappingStrategy<Reservation> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Reservation.class);

        new ReservationRetriever();
        HashMap<String, Reservation> reservations = ReservationRetriever.reservations;

        reservations.put(id, reservation);

        try (java.io.Writer writer = new FileWriter(filePath, false)) {
            StatefulBeanToCsv<Reservation> beanToCsv = new StatefulBeanToCsvBuilder<Reservation>(writer)
                    .withApplyQuotesToAll(false)
                    .withSeparator(',')
                    .withOrderedResults(true)
                    .build();
            beanToCsv.write(reservations.keySet().stream()
                    .mapToInt(Integer::valueOf)
                    .sorted()
                    .mapToObj(k-> reservations.get(String.valueOf(k)))
                    .toList());
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            System.err.println("Error saving reservations: " + e.getMessage());
        }
    }
}
