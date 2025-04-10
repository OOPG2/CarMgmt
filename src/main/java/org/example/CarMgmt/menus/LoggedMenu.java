package org.example.CarMgmt.menus;

import com.googlecode.lanterna.gui2.*;
import org.example.CarMgmt.App;
import org.example.CarMgmt.manager.AuthenticationManager;
import org.example.CarMgmt.objects.Admin;
import org.example.CarMgmt.objects.Customer;
import org.example.CarMgmt.objects.Staff;
import org.example.CarMgmt.objects.User;


import static org.example.CarMgmt.menus.postLogin.Admin.CustomerManagementMenu.showCustomerManagementMenu;
import static org.example.CarMgmt.menus.postLogin.Admin.StaffManagementMenu.showStaffManagementMenu;
import static org.example.CarMgmt.menus.ProfileMenu.showProfileMenu;
import static org.example.CarMgmt.menus.postLogin.Customer.CustomerVehicleManagementMenu.showCustomerVehicleManagementMenu;
import static org.example.CarMgmt.menus.postLogin.VehicleManagementMenu.showVehicleManagementMenu;
import static org.example.CarMgmt.menus.preLogin.MainMenu.showMainMenu;

public class LoggedMenu {
    public static void showLoggedMenu(MultiWindowTextGUI gui) {
        App app = new App();

        AuthenticationManager authenticationManager = app.getAuthenticationManager();
        User loggedUser = authenticationManager.getLoggedUser();

        BasicWindow showLoggedWindow = new BasicWindow("OOP Rentals");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        panel.addComponent(new Label(String.format("Hello %s,\nwhat would you like to do?", loggedUser.getName())));

        panel.addComponent(new EmptySpace());

        if (loggedUser instanceof Customer) {
            Button vehiclesButton = new Button("Collect/Return Vehicles", () -> {
                showLoggedWindow.close();
                showCustomerVehicleManagementMenu(gui);
            });
            Button reservationsButton = new Button("View Reservations", () -> {
                showLoggedWindow.close();
                showLoggedMenu(gui);
            });
            Button rentalsButton = new Button("View Past Rentals", () -> {
                showLoggedWindow.close();
                showLoggedMenu(gui);
            });
            Button invoicesButton = new Button("View Invoices", () -> {
                showLoggedWindow.close();
                showLoggedMenu(gui);
            });

            panel.addComponent(vehiclesButton);
            panel.addComponent(reservationsButton);
            panel.addComponent(rentalsButton);
            panel.addComponent(invoicesButton);
        }
        else if (loggedUser instanceof Staff) {
            Button vehiclesButton = new Button("Vehicle Management", () -> {
                showLoggedWindow.close();
                showVehicleManagementMenu(gui);
            });

            panel.addComponent(vehiclesButton);
        }
        else if (loggedUser instanceof Admin) {
            Button staffManagementButton = new Button("Staff Management", () -> {
                showLoggedWindow.close();
                showStaffManagementMenu(gui);
            });
            Button customerManagementButton = new Button("Customer Management", () -> {
                showLoggedWindow.close();
                showCustomerManagementMenu(gui);
            });
            Button vehiclesButton = new Button("Vehicle Management", () -> {
                showLoggedWindow.close();
                showVehicleManagementMenu(gui);
            });

            panel.addComponent(staffManagementButton);
            panel.addComponent(customerManagementButton);
            panel.addComponent(vehiclesButton);
        }

        Button profileButton = new Button("View Profile", () -> {
            showLoggedWindow.close();
            showProfileMenu(gui, null);
        });

        Button logoutButton = new Button("Logout", () -> {
            showLoggedWindow.close();
            authenticationManager.logoutUser();

            showMainMenu(gui);
        });

        panel.addComponent(profileButton);

        panel.addComponent(new EmptySpace());
        panel.addComponent(logoutButton);

        showLoggedWindow.setComponent(panel);
        gui.addWindowAndWait(showLoggedWindow);
    }
}
