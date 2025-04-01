package org.example.CarMgmt.menus.postLogin;

import com.googlecode.lanterna.gui2.*;
import org.example.CarMgmt.objects.User;

import static org.example.CarMgmt.menus.postLogin.ProfileMenu.showProfileMenu;
import static org.example.CarMgmt.menus.preLogin.MainMenu.showMainMenu;

public class LoggedMenu {
    public static void showLoggedMenu(MultiWindowTextGUI gui, User user) {
        BasicWindow showLoggedWindow = new BasicWindow("OOP Rentals");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        Button vehiclesButton = new Button("View Vehicles", () -> {
            showLoggedWindow.close();
            showMainMenu(gui);
        });
        Button profileButton = new Button("View Profile", () -> {
            showLoggedWindow.close();
            showProfileMenu(gui, user);
        });
        Button logoutButton = new Button("logout", () -> {
            showLoggedWindow.close();
            showMainMenu(gui);
        });

        panel.addComponent(profileButton);
        panel.addComponent(vehiclesButton);

        panel.addComponent(new EmptySpace());
        panel.addComponent(logoutButton);

        showLoggedWindow.setComponent(panel);
        gui.addWindowAndWait(showLoggedWindow);
    }
}
