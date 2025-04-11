package menus.postLogin;

import beans.*;
import csvParser.*;
import exceptions.*;
import app.*;
import static menus.postLogin.UpdateVehicleInfoMenu.showUpdateVehicleInfoMenu;
import static menus.postLogin.VehicleManagementMenu.showVehicleManagementMenu;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

import java.util.HashMap;
import java.util.regex.Pattern;

public class VehicleInfoMenu {
    static String displayedID = null;
    public static void showVehicleInfoMenu(MultiWindowTextGUI gui) {
        BasicWindow window = new BasicWindow("Vehicle Information");
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel searchPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        Panel infoPanel = new Panel(new GridLayout(2));
        Label id = new Label("Vehicle ID:");
        TextBox textBox = new TextBox().setValidationPattern(Pattern.compile("[1-9][0-9]*"));;
        Label brand = new Label("Brand: -");
        Label model = new Label("Model: -");
        Label type = new Label("Type: -");
        Label carplate = new Label("Car Plate: -");
        Label rental = new Label("Daily Rental: -");
        Label status = new Label("Status: -");
        Label condition = new Label("Condition: -");
        Label age = new Label("Age: -");
        Label seats = new Label("Seats: -");
        Label mileage = new Label("Mileage: -");
        infoPanel.addComponent(brand);
        infoPanel.addComponent(model);
        infoPanel.addComponent(type);
        infoPanel.addComponent(carplate);
        infoPanel.addComponent(rental);
        infoPanel.addComponent(status);
        infoPanel.addComponent(condition);
        infoPanel.addComponent(age);
        infoPanel.addComponent(seats);
        infoPanel.addComponent(mileage);
        Button backButton = new Button("Back", () -> {
            window.close();
            showVehicleManagementMenu(gui);
        });
        Button showButton = new Button("Show Info", () -> {
            String vehicleID = textBox.getText();
            try {
                VehicleRetriever vehicleRetriever = new VehicleRetriever();
                try {
                    Vehicle info = vehicleRetriever.retrieveById(vehicleID);
                    brand.setText("Brand: " + info.getBrand());
                    model.setText("Model: " + info.getModel());
                    type.setText("Type: " + info.getType());
                    carplate.setText("Car Plate: " + info.getCar_plate());
                    rental.setText("Rental: " + info.getDaily_rental());
                    status.setText("Status: " + info.getStatus());
                    condition.setText("Condition: " + info.getCondition());
                    age.setText("Age: " + info.getAge());
                    seats.setText("Seats: " + info.getSeats());
                    mileage.setText("Mileage: " + info.getMileage());
                    displayedID = vehicleID;
                }catch(RowNotFoundException e) {
                    new MessageDialogBuilder()
                    .setTitle("No Vehicle Found!")
                    .setText("There is no vehicle with the specified ID")
                    .build()
                    .showDialog(gui);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//            try {
//                BufferedReader reader = new BufferedReader(new FileReader("vehicles.csv"));
//                String line = reader.readLine();
//                while ((line = reader.readLine()) != null) {
//                    if (line.split(",")[0].equals(vehicleID)) {
//                        String[] info = line.split(",");
//                        brand.setText("Brand: " + info[1]);
//                        model.setText("Model: " + info[2]);
//                        type.setText("Type: " + info[3]);
//                        carplate.setText("Car Plate: " + info[4]);
//                        rental.setText("Rental: " + info[5]);
//                        status.setText("Status: " + info[6]);
//                        condition.setText("Condition: " + info[7]);
//                        age.setText("Age: " + info[8]);
//                        seats.setText("Seats: " + info[9]);
//                        mileage.setText("Mileage: " + info[10]);
//                        found = true;
//                        displayedID = vehicleID;
//                        break;
//                    }
//                }
//                if (!found){
//                    new MessageDialogBuilder()
//                            .setTitle("No org.example.CarMgmt.Beans.Vehicle Found!")
//                            .setText("There is no vehicle with the specified ID")
//                            .build()
//                            .showDialog(gui);
//                }
//
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
        });
        Button updateButton = new Button("Update Info", () -> {
            if (displayedID != null) {
                window.close();
                showUpdateVehicleInfoMenu(gui);
            }
            else{
                new MessageDialogBuilder()
                        .setTitle("No Vehicle Selected!")
                        .setText("There is no vehicle selected")
                        .build()
                        .showDialog(gui);
            }
        });
        searchPanel.addComponent(id);
        searchPanel.addComponent(textBox);
        searchPanel.addComponent(showButton);
        searchPanel.addComponent(updateButton);
        mainPanel.addComponent(searchPanel);
        mainPanel.addComponent(infoPanel);
        mainPanel.addComponent(backButton);
        window.setComponent(mainPanel);
        gui.addWindowAndWait(window);
    }
}
