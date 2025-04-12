package menus.postLogin;

import com.googlecode.lanterna.gui2.*;
import menus.LoggedMenu;

import static menus.postLogin.AddVehicleMenu.showAddVehicleMenu;
import static menus.postLogin.VehicleBookingMenu.showVehicleBookingMenu;
import static menus.postLogin.VehicleFleetMenu.showVehicleFleetMenu;
import static menus.postLogin.VehicleInfoMenu.showVehicleInfoMenu;

public class VehicleManagementMenu {
    public static void showVehicleManagementMenu(MultiWindowTextGUI gui){
        BasicWindow window = new BasicWindow("Vehicle Management");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        Button vehicleFleetButton = new Button("View Vehicle Fleet", () -> {
            window.close();
            showVehicleFleetMenu(gui);
        });
        Button vehicleInformationButton = new Button("View/Modify Vehicle Information", () -> {
            window.close();
            showVehicleInfoMenu(gui);

        });
        Button viewBookingButton = new Button("View Vehicle Bookings", () -> {
            window.close();
            showVehicleBookingMenu(gui);

        });
        Button addRemoveButton = new Button("Add Vehicle", () -> {
            window.close();
            showAddVehicleMenu(gui);
        });
        Button exitButton = new Button("Exit", () -> {
        	window.close();
        	LoggedMenu.showLoggedMenu();
        });
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
