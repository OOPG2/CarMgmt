package menus.postLogin.Customer;

import app.*;
import manager.*;
import objects.*;
import beans.*;
import csvParser.*;
import static menus.postLogin.Customer.CustomerVehicleManagementMenu.showCustomerVehicleManagementMenu;

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
import java.util.ArrayList;
import java.util.HashMap;

public class ReturnVehicleMenu {
    public static void showMenu(MultiWindowTextGUI gui) {
        App app = new App();
        AuthenticationManager authenticationManager = app.getAuthenticationManager();
        User loggedUser = authenticationManager.getLoggedUser();

        BasicWindow window = new BasicWindow("Vehicle Return");
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Table<String> table =new Table<>("Reservation ID","Vehicle ID","Customer ID","Status","Start Date","End Date","Daily Rental","Insurance Fee","Notes");
        try {
            ArrayList<Reservation> info = new ArrayList<>();
            HashMap<String, Reservation> hashmap = new ReservationRetriever().getReservations();
            hashmap.keySet().stream()
                    .filter(k->
                            hashmap.get(String.valueOf(k)).getCustomer_id().equals(loggedUser.getUserId()) &&
                            hashmap.get(String.valueOf(k)).getStatus().equals("Collected"))
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
                        .setTitle("No Vehicles To Return!")
                        .setText("You have no vehicles to return")
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
                reservation.setStatus("Completed");
                ReservationEditor.modifyRowInCsv(selectedID, reservation);
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
                new MessageDialogBuilder()
                        .setTitle("Vehicle Returned")
                        .setText("Thank you for renting with us! Hope to see you again soon!")
                        .build()
                        .showDialog(gui);
                ArrayList<Reservation> info = new ArrayList<>();
                HashMap<String, Reservation> hashmap = new ReservationRetriever().getReservations();
                hashmap.keySet().stream()
                        .filter(k->
                                hashmap.get(String.valueOf(k)).getCustomer_id().equals(loggedUser.getUserId()) &&
                                hashmap.get(String.valueOf(k)).getStatus().equals("Collected"))
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
                            .setTitle("No Vehicles To Return!")
                            .setText("You have no other vehicles to return")
                            .build()
                            .showDialog(gui);
                    window.close();
                    showCustomerVehicleManagementMenu(gui);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Label instructions = new Label("Press \"Enter\" To Return The Selected Vehicle");
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
