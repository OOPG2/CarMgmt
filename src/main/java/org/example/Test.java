package org.example;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) throws IOException {
        CsvParser csvParser = new CsvParser();
        try {
            csvParser.csvToHashmap(Reservation.class, "/reservations.csv");
            HashMap<String, CsvBeans> hashmap = csvParser.hashmap;
            hashmap.keySet().stream()
                    .mapToInt(Integer::valueOf)
                    .sorted()
                    .forEach(k->System.out.println(Arrays.toString(((Reservation) hashmap.get(String.valueOf(k))).getAll())));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
