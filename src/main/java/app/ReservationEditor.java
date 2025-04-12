package app;

import beans.*;
import constants.*;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReservationEditor {
    public static void modifyRowInCsv(String id, Reservation reservation) {
        try {
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
                        .withMappingStrategy(strategy)
                        .withApplyQuotesToAll(false)
                        .withSeparator(',')
                        .withOrderedResults(true)
                        .build();
                beanToCsv.write(reservations.keySet().stream()
                        .mapToInt(Integer::valueOf)
                        .sorted()
                        .mapToObj(k-> reservations.get(String.valueOf(k)))
                        .toList());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
