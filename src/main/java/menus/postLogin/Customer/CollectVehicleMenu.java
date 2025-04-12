package menus.postLogin.Customer;

import app.*;
import beans.Reservation;
import beans.Vehicle;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.table.Table;
import exceptions.RowNotFoundException;
import manager.AuthenticationManager;
import objects.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static menus.postLogin.Customer.CustomerVehicleManagementMenu.showCustomerVehicleManagementMenu;


public class CollectVehicleMenu {
    public static void showMenu(MultiWindowTextGUI gui) {
        App app = new App();
        AuthenticationManager authenticationManager = app.getAuthenticationManager();
        User loggedUser = authenticationManager.getLoggedUser();

        BasicWindow window = new BasicWindow("Vehicle Collection");
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Table<String> table =new Table<>("Reservation ID","Vehicle ID","Customer ID","Status","Start Date","End Date");
        try {
            ArrayList<Reservation> info = new ArrayList<>();
            HashMap<String, Reservation> hashmap = new ReservationRetriever().getReservations();
            hashmap.keySet().stream()
                    .filter(k->
                            hashmap.get(String.valueOf(k)).getUserId().equals(loggedUser.getUserId()) &&
                                    hashmap.get(String.valueOf(k)).getStatus().equals("Reserved") &&
                                    hashmap.get(String.valueOf(k)).getStartDateTime().isBefore(LocalDateTime.now()) &&
                                    hashmap.get(String.valueOf(k)).getEndDateTime().isAfter(LocalDateTime.now()))
                    .mapToInt(Integer::valueOf)
                    .sorted()
                    .forEach(k-> info.add(hashmap.get(String.valueOf(k))));
            if (!info.isEmpty()){
                table.getTableModel().clear();
                for (Reservation r:info){
                    table.getTableModel().addRow(r.getTableRow());
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
            String selectedID = table.getTableModel().getRow(table.getSelectedRow()).get(0);
            try {
                Reservation reservation = new ReservationRetriever().retrieveById(selectedID);
                reservation.setStatus("PickedUp");
                ReservationEditor.modifyRowInCsv(selectedID, reservation);
                Vehicle vehicle = new VehicleRetriever().retrieveById(String.valueOf(reservation.getVehicleId()));
                vehicle.setStatus("PickedUp");
                VehicleEditor.modifyRowInCsv(String.valueOf(vehicle.getVehicleID()),vehicle);
                new MessageDialogBuilder()
                        .setTitle("Vehicle Collected")
                        .setText("Thank you for renting with us! Please return your vehicle by " + reservation.getEnd())
                        .build()
                        .showDialog(gui);
                ArrayList<Reservation> info = new ArrayList<>();
                HashMap<String, Reservation> hashmap = new ReservationRetriever().getReservations();
                hashmap.keySet().stream()
                        .filter(k->
                                hashmap.get(String.valueOf(k)).getUserId().equals(loggedUser.getUserId()) &&
                                        hashmap.get(String.valueOf(k)).getStatus().equals("Reserved") &&
                                        hashmap.get(String.valueOf(k)).getStartDateTime().isBefore(LocalDateTime.now()) &&
                                        hashmap.get(String.valueOf(k)).getEndDateTime().isAfter(LocalDateTime.now()))
                        .mapToInt(Integer::valueOf)
                        .sorted()
                        .forEach(k-> info.add(hashmap.get(String.valueOf(k))));
                if (!info.isEmpty()){
                    table.getTableModel().clear();
                    for (Reservation r:info){
                        table.getTableModel().addRow(r.getTableRow());
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
