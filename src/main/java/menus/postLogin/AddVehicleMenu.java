package menus.postLogin;

import app.VehicleRetriever;
import app.VehicleWriter;
import beans.Vehicle;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

import java.util.regex.Pattern;

import static menus.postLogin.VehicleManagementMenu.showVehicleManagementMenu;

public class AddVehicleMenu {
    public static void showAddVehicleMenu(MultiWindowTextGUI gui) {
        BasicWindow window = new BasicWindow("Add Vehicle");
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Panel editPanel = new Panel();
        editPanel.setLayoutManager(new GridLayout(4));
        Label brand = new Label("Brand:");
        TextBox brandText = new TextBox();
        Label model = new Label("Model:");
        TextBox modelText = new TextBox();
        Label type = new Label("Type:");
        TextBox typeText = new TextBox();
        Label carplate = new Label("Car Plate:");
        TextBox carplateText = new TextBox();
        Label rental = new Label("Daily Rental:");
        TextBox rentalText = new TextBox().setValidationPattern(Pattern.compile("[1-9][0-9]*"));
        Label status = new Label("Status:");
        ComboBox<String> statusBox = new ComboBox<String>();
        statusBox.addItem("Available");
        statusBox.addItem("Reserved");
        statusBox.addItem("Collected");
        statusBox.addItem("Maintenance");
        Label condition = new Label("Condition:");
        ComboBox<String> conditionBox = new ComboBox<String>();
        conditionBox.addItem("Good");
        conditionBox.addItem("Dented");
        conditionBox.addItem("Scratched");
        Label age = new Label("Age:");
        TextBox ageText = new TextBox().setValidationPattern(Pattern.compile("[1-9][0-9]*"));
        Label seats = new Label("Seats:");
        TextBox seatsText = new TextBox().setValidationPattern(Pattern.compile("[1-9][0-9]*"));
        Label mileage = new Label("Mileage:");
        TextBox mileageText = new TextBox().setValidationPattern(Pattern.compile("[1-9][0-9]*"));
        editPanel.addComponent(brand);
        editPanel.addComponent(brandText);
        editPanel.addComponent(model);
        editPanel.addComponent(modelText);
        editPanel.addComponent(type);
        editPanel.addComponent(typeText);
        editPanel.addComponent(carplate);
        editPanel.addComponent(carplateText);
        editPanel.addComponent(rental);
        editPanel.addComponent(rentalText);
        editPanel.addComponent(status);
        editPanel.addComponent(statusBox);
        editPanel.addComponent(condition);
        editPanel.addComponent(conditionBox);
        editPanel.addComponent(age);
        editPanel.addComponent(ageText);
        editPanel.addComponent(seats);
        editPanel.addComponent(seatsText);
        editPanel.addComponent(mileage);
        editPanel.addComponent(mileageText);

        Button saveButton = new Button("Add", () -> {
            Vehicle vehicle = null;
            if (brandText.getText().isBlank()||
                    modelText.getText().isBlank()||
                    typeText.getText().isBlank()||
                    carplateText.getText().isBlank()||
                    rentalText.getText().isBlank()||
                    statusBox.getSelectedItem().isBlank()||
                    conditionBox.getSelectedItem().isBlank()||
                    ageText.getText().isBlank()||
                    seatsText.getText().isBlank()||
                    mileageText.getText().isBlank())
            {
                new MessageDialogBuilder()
                        .setTitle("Missing Information!")
                        .setText("Please fill in all fields!")
                        .build()
                        .showDialog(gui);
            }
            else {
                try {
                    vehicle = new Vehicle(
                            new VehicleRetriever().getCurrentLastRowId()+1,
                            brandText.getText(),
                            modelText.getText(),
                            typeText.getText(),
                            carplateText.getText(),
                            Double.parseDouble(rentalText.getText()),
                            statusBox.getSelectedItem(),
                            conditionBox.getSelectedItem(),
                            Integer.parseInt(ageText.getText()),
                            Integer.parseInt(seatsText.getText()),
                            Integer.parseInt(mileageText.getText()));
                    new VehicleWriter().writeToCsv(vehicle);
//                    hashmap.put(vehicle.getId(), vehicle);
//                    OutputStream out = new FileOutputStream("databases/vehicles.csv");
//                    OutputStreamWriter writer = new OutputStreamWriter(out);
//                    StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
//                    hashmap.keySet().stream()
//                            .mapToInt(Integer::valueOf)
//                            .sorted()
//                            .forEach(k -> {
//                                try {
//                                    beanToCsv.write(hashmap.get(String.valueOf(k)));
//                                } catch (CsvDataTypeMismatchException e) {
//                                    e.printStackTrace();
//                                } catch (CsvRequiredFieldEmptyException e) {
//                                    e.printStackTrace();
//                                }
//                            });
//                    writer.flush();
//                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                window.close();
                assert vehicle != null;
                new MessageDialogBuilder()
                        .setTitle("Vehicle Added!")
                        .setText(vehicle.getMake() + " " + vehicle.getModel() + " has been added with ID " + vehicle.getVehicleID())
                        .build()
                        .showDialog(gui);
                showAddVehicleMenu(gui);
            }
        });
        Button backButton = new Button("Back", () -> {
            window.close();
            showVehicleManagementMenu(gui);
        });
        mainPanel.addComponent(editPanel);
        Panel buttonsPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        buttonsPanel.addComponent(backButton);
        buttonsPanel.addComponent(saveButton);
        mainPanel.addComponent(buttonsPanel);
        window.setComponent(mainPanel);
        gui.addWindowAndWait(window);
    }
}

