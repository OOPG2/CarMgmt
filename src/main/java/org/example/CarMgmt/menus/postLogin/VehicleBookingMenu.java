package org.example.CarMgmt.menus.postLogin;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.table.Table;
import org.example.CarMgmt.Beans.Reservation;
import org.example.CarMgmt.ReservationRetriever;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import static org.example.CarMgmt.menus.postLogin.VehicleManagementMenu.showVehicleManagementMenu;

public class VehicleBookingMenu {
    public static void showVehicleBookingMenu(MultiWindowTextGUI gui) {
        BasicWindow window = new BasicWindow("Vehicle Bookings");
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Panel panel1 = new Panel();
        panel1.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        Label id = new Label("Vehicle ID:");
        TextBox textBox = new TextBox().setValidationPattern(Pattern.compile("[1-9][0-9]*"));;
        panel1.addComponent(id);
        panel1.addComponent(textBox);
        mainPanel.addComponent(panel1);

        Table<String> table =new Table<>("Reservation ID","Customer ID","Vehicle ID","Status","Start Date","End Date","Daily Rental","Insurance Fee","Notes");
        try {
            HashMap<String, Reservation> hashmap = new ReservationRetriever().getReservations();
            hashmap.keySet().stream()
                    .mapToInt(Integer::valueOf)
                    .sorted()
                    .forEach(k->table.getTableModel().addRow(( hashmap.get(String.valueOf(k))).getAll()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        table.setVisibleRows(10);
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("/reservations.csv"));
//            String line = reader.readLine();
//            while ((line = reader.readLine()) != null) {
//                table.getTableModel().addRow(line.split(","));
//            }
//            table.setVisibleRows(10);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
        mainPanel.addComponent(table);
        Button backButton = new Button("Back", () -> {
            window.close();
            showVehicleManagementMenu(gui);
        });
        Button filterButton = new Button("Filter Reservations", () -> {
            String vehicleID = textBox.getText();
            if (vehicleID.isEmpty()){
                try {
                    HashMap<String, Reservation> hashmap = new ReservationRetriever().getReservations();
                    table.getTableModel().clear();
                    hashmap.keySet().stream()
                            .mapToInt(Integer::valueOf)
                            .sorted()
                            .forEach(k->table.getTableModel().addRow(hashmap.get(String.valueOf(k)).getAll()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                ArrayList<Reservation> info = new ArrayList<>();
                try {
                    HashMap<String, Reservation> hashmap = new ReservationRetriever().getReservations();
                    hashmap.keySet().stream()
                            .filter(k -> vehicleID.equals(hashmap.get(k).getVehicle_id()))
                            .mapToInt(Integer::valueOf)
                            .sorted()
                            .forEach(k -> info.add(hashmap.get(String.valueOf(k))));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (!info.isEmpty()){
                    table.getTableModel().clear();
                    for (Reservation r:info){
                        table.getTableModel().addRow(r.getAll());
                    }
                }
                else{
                    new MessageDialogBuilder()
                            .setTitle("No Reservation Found!")
                            .setText("There is no reservations for the specified vehicle ID")
                            .build()
                            .showDialog(gui);
                }
            }
//            try {
//                BufferedReader reader = new BufferedReader(new FileReader("reservations.csv"));
//                String line = reader.readLine();
//                ArrayList<String> info = new ArrayList<>();
//                while ((line = reader.readLine()) != null) {
//                    if (line.split(",")[1].equals(vehicleID)||vehicleID.isEmpty()) {
//                        info.add(line);
//                        found = true;
//                    }
//                }
//                if (!found){
//                    new MessageDialogBuilder()
//                            .setTitle("No Reservation Found!")
//                            .setText("There is no reservations for the specified vehicle ID")
//                            .build()
//                            .showDialog(gui);
//                }
//                else {
//                    table.getTableModel().clear();
//                    for (String s: info) {
//                        table.getTableModel().addRow(s.split(","));
//                    }
//                }
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
        });
        Panel panel2 = new Panel();
        panel2.setLayoutManager(new GridLayout(2));
        panel2.addComponent(backButton);
        panel1.addComponent(filterButton);
        mainPanel.addComponent(panel2);
        window.setComponent(mainPanel);
        gui.addWindowAndWait(window);
    }
}
