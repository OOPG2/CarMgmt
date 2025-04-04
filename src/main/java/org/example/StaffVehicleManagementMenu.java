package org.example;

import com.googlecode.lanterna.gui2.*;

public class StaffVehicleManagementMenu {
    public static void showMenu(MultiWindowTextGUI gui){
        BasicWindow window = new BasicWindow("Vehicle Management");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        Button vehicleFleetButton = new Button("View Vehicle Fleet", () -> {
            window.close();
            VehicleFleetMenu.showMenu(gui);
        });
        Button vehicleInformationButton = new Button("View/Update Vehicle Information", () -> {
            window.close();
            VehicleInfoMenu.showMenu(gui);

        });
        Button viewBookingButton = new Button("View Vehicle Bookings", () -> {
            window.close();
            VehicleBookingMenu.showMenu(gui);

        });
        Button addRemoveButton = new Button("Add Vehicle", () -> {
            window.close();
            AddVehicleMenu.showMenu(gui);
        });
        Button exitButton = new Button("Exit", window::close);
        panel.addComponent(vehicleFleetButton);
        panel.addComponent(vehicleInformationButton);
        panel.addComponent(viewBookingButton);
        panel.addComponent(addRemoveButton);
        panel.addComponent(new EmptySpace());
        panel.addComponent(exitButton);
        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }
}
