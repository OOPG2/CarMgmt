package org.example.CarMgmt.menus.postLogin;

import com.googlecode.lanterna.gui2.*;
import org.example.CarMgmt.manager.AuthenticationManager;
import org.example.CarMgmt.objects.Admin;
import org.example.CarMgmt.objects.Customer;
import org.example.CarMgmt.objects.Staff;
import org.example.CarMgmt.objects.User;

import static org.example.CarMgmt.menus.postLogin.Admin.CustomerManagementMenu.showCustomerManagementMenu;
import static org.example.CarMgmt.menus.postLogin.Admin.StaffManagementMenu.showStaffManagementMenu;
import static org.example.CarMgmt.menus.postLogin.ProfileMenu.showProfileMenu;
import static org.example.CarMgmt.menus.preLogin.MainMenu.showMainMenu;

public class LoggedMenu {
    public static void showLoggedMenu(MultiWindowTextGUI gui) {
        User loggedUser = AuthenticationManager.getLoggedUser();

        BasicWindow showLoggedWindow = new BasicWindow("OOP Rentals");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        if (loggedUser instanceof Customer) {
            Button vehiclesButton = new Button("View All Available Vehicles", () -> {
                showLoggedWindow.close();
                showLoggedMenu(gui);
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
            Button vehiclesButton = new Button("View Entire Vehicle Fleet", () -> {
                showLoggedWindow.close();
                showLoggedMenu(gui);
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
            Button vehiclesButton = new Button("View Vehicles", () -> {
                showLoggedWindow.close();
                showLoggedMenu(gui);
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
            AuthenticationManager.logoutUser();

            showMainMenu(gui);
        });

        panel.addComponent(profileButton);

        panel.addComponent(new EmptySpace());
        panel.addComponent(logoutButton);

        showLoggedWindow.setComponent(panel);
        gui.addWindowAndWait(showLoggedWindow);
    }
}
