package org.example;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class UpdateVehicleInfoMenu {
    public static void showMenu(MultiWindowTextGUI gui) {
        String vehicleID;
        int prev;
        if (!Objects.isNull(VehicleInfoMenu.displayedID)){
            vehicleID = VehicleInfoMenu.displayedID;
            prev = 1;
        } else if (!Objects.isNull(VehicleFleetMenu.selectedID)) {
            vehicleID = VehicleFleetMenu.selectedID;
            prev = 2;
        } else {
            vehicleID= "";
            prev = 0;
        }
        BasicWindow window = new BasicWindow("Vehicle Information");
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        Label id = new Label("Vehicle ID: " + vehicleID);
        mainPanel.addComponent(id);
        CsvParser csvParser = new CsvParser();
        try {
            csvParser.csvToHashmap(Vehicle.class, "vehicles.csv");
            HashMap<String, CsvBeans> hashmap = csvParser.hashmap;
            Vehicle info = (Vehicle) hashmap.get(vehicleID);
            Panel editPanel = new Panel();
            editPanel.setLayoutManager(new GridLayout(4));
            Label brand = new Label("Brand:");
            TextBox brandText = new TextBox(info.getBrand());
            Label model = new Label("Model:");
            TextBox modelText = new TextBox(info.getModel());
            Label type = new Label("Type:");
            TextBox typeText = new TextBox(info.getType());
            Label carplate = new Label("Car Plate:");
            TextBox carplateText = new TextBox(info.getCar_plate());
            Label rental = new Label("Daily Rental:");
            TextBox rentalText = new TextBox(info.getDaily_rental()).setValidationPattern(Pattern.compile("[1-9][0-9]*"));
            Label status = new Label("Status:");
            ComboBox<String> statusBox = new ComboBox<String>();
            statusBox.addItem("Available");
            statusBox.addItem("Reserved");
            statusBox.addItem("Collected");
            statusBox.addItem("Maintenance");
            statusBox.setSelectedItem(info.getStatus());
            Label condition = new Label("Condition:");
            ComboBox<String> conditionBox = new ComboBox<String>();
            conditionBox.addItem("Good");
            conditionBox.addItem("Dented");
            conditionBox.addItem("Scratched");
            conditionBox.setSelectedItem(info.getCondition());
            Label age = new Label("Age:");
            TextBox ageText = new TextBox(info.getAge()).setValidationPattern(Pattern.compile("[1-9][0-9]*"));
            Label seats = new Label("Seats:");
            TextBox seatsText = new TextBox(info.getSeats()).setValidationPattern(Pattern.compile("[1-9][0-9]*"));
            Label mileage = new Label("Mileage:");
            TextBox mileageText = new TextBox(info.getMileage()).setValidationPattern(Pattern.compile("[1-9][0-9]*"));
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
            Button saveButton = new Button("Save", () -> {
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
                    info.setBrand(brandText.getText());
                    info.setModel(modelText.getText());
                    info.setType(typeText.getText());
                    info.setCar_plate(carplateText.getText());
                    info.setDaily_rental(rentalText.getText());
                    info.setStatus(statusBox.getSelectedItem());
                    info.setCondition(conditionBox.getSelectedItem());
                    info.setAge(ageText.getText());
                    info.setSeats(seatsText.getText());
                    info.setMileage(mileageText.getText());
                    try {
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
                    VehicleInfoMenu.displayedID = null;
                    VehicleFleetMenu.selectedID = null;
                    window.close();
                    switch (prev) {
                        case 1 -> VehicleInfoMenu.showMenu(gui);
                        case 2 -> VehicleFleetMenu.showMenu(gui);
                    }
                }
            });
            Button deleteButton = new Button("Remove Vehicle From Fleet", () ->{
                hashmap.remove(vehicleID);
                try {
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
                VehicleInfoMenu.displayedID = null;
                VehicleFleetMenu.selectedID = null;
                window.close();
                switch (prev) {
                    case 1 -> VehicleInfoMenu.showMenu(gui);
                    case 2 -> VehicleFleetMenu.showMenu(gui);
                }
            });
            Button backButton = new Button("Back", () -> {
                window.close();
                VehicleInfoMenu.displayedID = null;
                VehicleFleetMenu.selectedID = null;
                switch (prev){
                    case 1 -> VehicleInfoMenu.showMenu(gui);
                    case 2 -> VehicleFleetMenu.showMenu(gui);
                }
            });
            mainPanel.addComponent(editPanel);
            Panel buttonsPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
            buttonsPanel.addComponent(backButton);
            buttonsPanel.addComponent(saveButton);
            buttonsPanel.addComponent(deleteButton);
            mainPanel.addComponent(buttonsPanel);
            window.setComponent(mainPanel);
            gui.addWindowAndWait(window);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("vehicles.csv"));
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                if (line.split(",")[0].equals(vehicleID)) {
//                    String[] info = line.split(",");
//                    Panel editPanel = new Panel();
//                    editPanel.setLayoutManager(new GridLayout(4));
//                    Label brand = new Label("Brand:");
//                    TextBox brandText = new TextBox(info[1]);
//                    Label model = new Label("Model:");
//                    TextBox modelText = new TextBox(info[2]);
//                    Label type = new Label("Type:");
//                    TextBox typeText = new TextBox(info[3]);
//                    Label carplate = new Label("Car Plate:");
//                    TextBox carplateText = new TextBox(info[4]);
//                    Label rental = new Label("Daily Rental:");
//                    TextBox rentalText = new TextBox(info[5]);
//                    Label status = new Label("Status:");
//                    TextBox statusText = new TextBox(info[6]);
//                    Label condition = new Label("Condition:");
//                    TextBox conditionText = new TextBox(info[7]);
//                    Label age = new Label("Age:");
//                    TextBox ageText = new TextBox(info[8]);
//                    Label seats = new Label("Seats:");
//                    TextBox seatsText = new TextBox(info[9]);
//                    Label mileage = new Label("Mileage:");
//                    TextBox mileageText = new TextBox(info[10]);
//                    editPanel.addComponent(brand);
//                    editPanel.addComponent(brandText);
//                    editPanel.addComponent(model);
//                    editPanel.addComponent(modelText);
//                    editPanel.addComponent(type);
//                    editPanel.addComponent(typeText);
//                    editPanel.addComponent(carplate);
//                    editPanel.addComponent(carplateText);
//                    editPanel.addComponent(rental);
//                    editPanel.addComponent(rentalText);
//                    editPanel.addComponent(status);
//                    editPanel.addComponent(statusText);
//                    editPanel.addComponent(condition);
//                    editPanel.addComponent(conditionText);
//                    editPanel.addComponent(age);
//                    editPanel.addComponent(ageText);
//                    editPanel.addComponent(seats);
//                    editPanel.addComponent(seatsText);
//                    editPanel.addComponent(mileage);
//                    editPanel.addComponent(mileageText);
//                    Button saveButton = new Button("Save", () -> {
//                        try {
//                            BufferedReader reader2 = new BufferedReader(new FileReader("vehicles.csv"));
//                            StringBuilder text = new StringBuilder();
//                            String row = "";
//                            while ((row = reader2.readLine()) != null) {
//                                if (row.split(",")[0].equals(vehicleID)) {
//                                    text.append(vehicleID).append(",")
//                                            .append(brandText.getText()).append(",")
//                                            .append(modelText.getText()).append(",")
//                                            .append(typeText.getText()).append(",")
//                                            .append(carplateText.getText()).append(",")
//                                            .append(rentalText.getText()).append(",")
//                                            .append(statusText.getText()).append(",")
//                                            .append(conditionText.getText()).append(",")
//                                            .append(ageText.getText()).append(",")
//                                            .append(seatsText.getText()).append(",")
//                                            .append(mileageText.getText()).append("\n");
//                                } else {
//                                    text.append(row).append("\n");
//                                }
//                            }
//                            BufferedWriter writer = new BufferedWriter(new FileWriter("vehicles.csv"));
//                            writer.write(String.valueOf(text));
//                            writer.flush();
//                            VehicleInfoMenu.displayedID = null;
//                            window.close();
//                            VehicleInfoMenu.showMenu(gui);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
//                    Button backButton = new Button("Back", () -> {
//                        window.close();
//                        VehicleInfoMenu.displayedID = null;
//                        VehicleInfoMenu.showMenu(gui);
//                    });
//                    mainPanel.addComponent(editPanel);
//                    Panel buttonsPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
//                    buttonsPanel.addComponent(backButton);
//                    buttonsPanel.addComponent(saveButton);
//                    mainPanel.addComponent(buttonsPanel);
//                    window.setComponent(mainPanel);
//                    gui.addWindowAndWait(window);
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
}

