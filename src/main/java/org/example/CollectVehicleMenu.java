package org.example;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.table.Table;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class CollectVehicleMenu {
    public static void showMenu(MultiWindowTextGUI gui) {
        String customerID = "1";
        BasicWindow window = new BasicWindow("Vehicle Collection");
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Table<String> table =new Table<>("Reservation ID","Vehicle ID","Customer ID","Status","Start Date","End Date","Daily Rental","Insurance Fee","Notes");
        CsvParser csvParser = new CsvParser();
        try {
            ArrayList<Reservation> info = new ArrayList<>();
            csvParser.csvToHashmap(Reservation.class, "reservations.csv");
            HashMap<String, CsvBeans> hashmap = csvParser.hashmap;
            hashmap.keySet().stream()
                    .filter(k->
                            ((Reservation) hashmap.get(String.valueOf(k))).getCustomer_id().equals(customerID) &&
                            ((Reservation) hashmap.get(String.valueOf(k))).getStatus().equals("Confirmed") &&
                            (LocalDate.parse(((Reservation) hashmap.get(String.valueOf(k))).getStart_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isBefore(LocalDate.now()) ||
                            LocalDate.parse(((Reservation) hashmap.get(String.valueOf(k))).getStart_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isEqual(LocalDate.now()))&&
                            LocalDate.parse(((Reservation) hashmap.get(String.valueOf(k))).getEnd_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isAfter(LocalDate.now()) ||
                            LocalDate.parse(((Reservation) hashmap.get(String.valueOf(k))).getEnd_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isEqual(LocalDate.now()))
                    .mapToInt(Integer::valueOf)
                    .sorted()
                    .forEach(k-> info.add((Reservation) hashmap.get(String.valueOf(k))));
            if (!info.isEmpty()){
                table.getTableModel().clear();
                for (Reservation r:info){
                    table.getTableModel().addRow(r.getAll());
                }
            }
            else{
                new MessageDialogBuilder()
                        .setTitle("No Vehicles For Collection!")
                        .setText("You have no vehicles ready for collection")
                        .build()
                        .showDialog(gui);
                CustomerVehicleManagementMenu.showMenu(gui);
                return;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        table.setVisibleRows(10);
        table.setSelectAction(() -> {
            String selectedID = table.getTableModel().getRow(table.getSelectedRow()).getFirst();
            HashMap<String, CsvBeans> hashmap = csvParser.hashmap;
            ((Reservation) hashmap.get(selectedID)).setStatus("Collected");
            try {
                OutputStream out = new FileOutputStream("reservations.csv");
                OutputStreamWriter writer = new OutputStreamWriter(out);
                StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
                hashmap.keySet().stream()
                        .mapToInt(Integer::valueOf)
                        .sorted()
                        .forEach(k -> {
                            try {
                                beanToCsv.write(hashmap.get(String.valueOf(k)));
                            } catch (CsvDataTypeMismatchException e) {
                                e.printStackTrace();
                            } catch (CsvRequiredFieldEmptyException e) {
                                e.printStackTrace();
                            }
                        });
                writer.flush();
                writer.close();
                new MessageDialogBuilder()
                        .setTitle("Vehicle Collected")
                        .setText("Thank you for renting with us! Please return your vehicle by " + ((Reservation) hashmap.get(selectedID)).getEnd_date())
                        .build()
                        .showDialog(gui);
                ArrayList<Reservation> info = new ArrayList<>();
                hashmap.keySet().stream()
                        .filter(k->
                                ((Reservation) hashmap.get(String.valueOf(k))).getCustomer_id().equals(customerID) &&
                                ((Reservation) hashmap.get(String.valueOf(k))).getStatus().equals("Confirmed") &&
                                (LocalDate.parse(((Reservation) hashmap.get(String.valueOf(k))).getStart_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isBefore(LocalDate.now()) ||
                                LocalDate.parse(((Reservation) hashmap.get(String.valueOf(k))).getStart_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isEqual(LocalDate.now()))&&
                                LocalDate.parse(((Reservation) hashmap.get(String.valueOf(k))).getEnd_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isAfter(LocalDate.now()) ||
                                LocalDate.parse(((Reservation) hashmap.get(String.valueOf(k))).getEnd_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isEqual(LocalDate.now()))
                        .mapToInt(Integer::valueOf)
                        .sorted()
                        .forEach(k-> info.add((Reservation) hashmap.get(String.valueOf(k))));
                if (!info.isEmpty()){
                    table.getTableModel().clear();
                    for (Reservation r:info){
                        table.getTableModel().addRow(r.getAll());
                    }
                }
                else{
                    new MessageDialogBuilder()
                            .setTitle("No Vehicles For Collection!")
                            .setText("You have no other vehicles ready for collection")
                            .build()
                            .showDialog(gui);
                    window.close();
                    CustomerVehicleManagementMenu.showMenu(gui);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        panel.addComponent(table);
        Button backButton = new Button("Back", () -> {
            window.close();
            CustomerVehicleManagementMenu.showMenu(gui);
        });
        panel.addComponent(backButton);
        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }
}
