package menus.postLogin.Customer;

import app.*;
import exceptions.*;
import manager.*;
import objects.*;
import beans.*;
import static menus.postLogin.Customer.CustomerVehicleManagementMenu.showCustomerVehicleManagementMenu;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.table.Table;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class CollectVehicleMenu {
    public static void showMenu(MultiWindowTextGUI gui) {
        App app = new App();
        AuthenticationManager authenticationManager = app.getAuthenticationManager();
        User loggedUser = authenticationManager.getLoggedUser();

        BasicWindow window = new BasicWindow("Vehicle Collection");
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Table<String> table =new Table<>("Reservation ID","Vehicle ID","Customer ID","Status","Start Date","End Date","Daily Rental","Insurance Fee","Notes");
        try {
            ArrayList<Reservation> info = new ArrayList<>();
            HashMap<String, Reservation> hashmap = new ReservationRetriever().getReservations();
            hashmap.keySet().stream()
                    .filter(k->
                            hashmap.get(String.valueOf(k)).getCustomer_id().equals(loggedUser.getUserId()) &&
                            hashmap.get(String.valueOf(k)).getStatus().equals("Confirmed") &&
                            (LocalDate.parse(hashmap.get(String.valueOf(k)).getStart_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isBefore(LocalDate.now()) ||
                            LocalDate.parse(hashmap.get(String.valueOf(k)).getStart_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isEqual(LocalDate.now()))&&
                            (LocalDate.parse(hashmap.get(String.valueOf(k)).getEnd_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isAfter(LocalDate.now()) ||
                            LocalDate.parse(hashmap.get(String.valueOf(k)).getEnd_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isEqual(LocalDate.now())))
                    .mapToInt(Integer::valueOf)
                    .sorted()
                    .forEach(k-> info.add(hashmap.get(String.valueOf(k))));
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
                showCustomerVehicleManagementMenu(gui);
                return;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        table.setVisibleRows(10);
        table.setSelectAction(() -> {
            String selectedID = table.getTableModel().getRow(table.getSelectedRow()).getFirst();
            try {
                Reservation reservation = new ReservationRetriever().retrieveById(selectedID);
                reservation.setStatus("Collected");
                ReservationEditor.modifyRowInCsv(selectedID, reservation);
                new MessageDialogBuilder()
                        .setTitle("Vehicle Collected")
                        .setText("Thank you for renting with us! Please return your vehicle by " + reservation.getEnd_date())
                        .build()
                        .showDialog(gui);
                ArrayList<Reservation> info = new ArrayList<>();
                HashMap<String, Reservation> hashmap = new ReservationRetriever().getReservations();
                hashmap.keySet().stream()
                        .filter(k->
                                hashmap.get(String.valueOf(k)).getCustomer_id().equals(loggedUser.getUserId()) &&
                                        hashmap.get(String.valueOf(k)).getStatus().equals("Confirmed") &&
                                        (LocalDate.parse(hashmap.get(String.valueOf(k)).getStart_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isBefore(LocalDate.now()) ||
                                                LocalDate.parse(hashmap.get(String.valueOf(k)).getStart_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isEqual(LocalDate.now()))&&
                                        (LocalDate.parse(hashmap.get(String.valueOf(k)).getEnd_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isAfter(LocalDate.now()) ||
                                                LocalDate.parse(hashmap.get(String.valueOf(k)).getEnd_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isEqual(LocalDate.now())))
                        .mapToInt(Integer::valueOf)
                        .sorted()
                        .forEach(k-> info.add(hashmap.get(String.valueOf(k))));
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
                    showCustomerVehicleManagementMenu(gui);
                }
            } catch (RowNotFoundException e) {
                throw new RuntimeException(e);
            }
//            try {
//                OutputStream out = new FileOutputStream("databases/reservations.csv");
//                OutputStreamWriter writer = new OutputStreamWriter(out);
//                StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
//                hashmap.keySet().stream()
//                        .mapToInt(Integer::valueOf)
//                        .sorted()
//                        .forEach(k -> {
//                            try {
//                                beanToCsv.write(hashmap.get(String.valueOf(k)));
//                            } catch (CsvDataTypeMismatchException e) {
//                                e.printStackTrace();
//                            } catch (CsvRequiredFieldEmptyException e) {
//                                e.printStackTrace();
//                            }
//                        });
//                writer.flush();
//                writer.close();
//                new MessageDialogBuilder()
//                        .setTitle("Vehicle Collected")
//                        .setText("Thank you for renting with us! Please return your vehicle by " + hashmap.get(selectedID).getEnd_date())
//                        .build()
//                        .showDialog(gui);
//                ArrayList<Reservation> info = new ArrayList<>();
//                hashmap.keySet().stream()
//                        .filter(k->
//                                hashmap.get(String.valueOf(k)).getCustomer_id().equals(loggedUser.getUserId()) &&
//                                hashmap.get(String.valueOf(k)).getStatus().equals("Confirmed") &&
//                                (LocalDate.parse(hashmap.get(String.valueOf(k)).getStart_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isBefore(LocalDate.now()) ||
//                                LocalDate.parse(hashmap.get(String.valueOf(k)).getStart_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isEqual(LocalDate.now()))&&
//                                (LocalDate.parse(hashmap.get(String.valueOf(k)).getEnd_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isAfter(LocalDate.now()) ||
//                                LocalDate.parse(hashmap.get(String.valueOf(k)).getEnd_date(), DateTimeFormatter.ofPattern("d/M/yy", Locale.ENGLISH)).isEqual(LocalDate.now())))
//                        .mapToInt(Integer::valueOf)
//                        .sorted()
//                        .forEach(k-> info.add(hashmap.get(String.valueOf(k))));
//                if (!info.isEmpty()){
//                    table.getTableModel().clear();
//                    for (Reservation r:info){
//                        table.getTableModel().addRow(r.getAll());
//                    }
//                }
//                else{
//                    new MessageDialogBuilder()
//                            .setTitle("No Vehicles For Collection!")
//                            .setText("You have no other vehicles ready for collection")
//                            .build()
//                            .showDialog(gui);
//                    window.close();
//                    showCustomerVehicleManagementMenu(gui);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        });
        Label instructions = new Label("Press \"Enter\" To Collect The Selected Vehicle");
        panel.addComponent(instructions);
        panel.addComponent(table);
        Button backButton = new Button("Back", () -> {
            window.close();
            showCustomerVehicleManagementMenu(gui);
        });
        panel.addComponent(backButton);
        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }
}
