package org.example;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Objects;

public class AddVehicleMenu {
    public static void showMenu(MultiWindowTextGUI gui) {
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
        TextBox rentalText = new TextBox();
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
        TextBox ageText = new TextBox();
        Label seats = new Label("Seats:");
        TextBox seatsText = new TextBox();
        Label mileage = new Label("Mileage:");
        TextBox mileageText = new TextBox();
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
        Vehicle vehicle = new Vehicle();
        Button saveButton = new Button("Add", () -> {
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
                    vehicle.setBrand(brandText.getText());
                    vehicle.setModel(modelText.getText());
                    vehicle.setType(typeText.getText());
                    vehicle.setCar_plate(carplateText.getText());
                    vehicle.setDaily_rental(rentalText.getText());
                    vehicle.setStatus(statusBox.getSelectedItem());
                    vehicle.setCondition(conditionBox.getSelectedItem());
                    vehicle.setAge(ageText.getText());
                    vehicle.setSeats(seatsText.getText());
                    vehicle.setMileage(mileageText.getText());
                    CsvParser csvParser = new CsvParser();
                    csvParser.csvToHashmap(Vehicle.class, "vehicles.csv");
                    HashMap<String, CsvBeans> hashmap = csvParser.hashmap;
                    vehicle.setVehicle_id(String.valueOf(hashmap.keySet().stream()
                            .mapToInt(Integer::valueOf)
                            .max().orElse(0) + 1));
                    hashmap.put(vehicle.getId(), vehicle);
                    OutputStream out = new FileOutputStream("vehicles.csv");
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                window.close();
                new MessageDialogBuilder()
                        .setTitle("Vehicle Added!")
                        .setText(vehicle.getBrand() + " " + vehicle.getModel() + " has been added with ID " + vehicle.getVehicle_id())
                        .build()
                        .showDialog(gui);
                showMenu(gui);
            }
        });
        Button backButton = new Button("Back", () -> {
            window.close();
            StaffVehicleManagementMenu.showMenu(gui);
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

