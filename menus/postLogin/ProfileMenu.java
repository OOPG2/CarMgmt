package org.example.CarMgmt.menus.postLogin;

import org.example.CarMgmt.objects.User;

import com.googlecode.lanterna.gui2.*;

import static org.example.CarMgmt.menus.postLogin.LoggedMenu.showLoggedMenu;

public class ProfileMenu {
    public static void showProfileMenu(MultiWindowTextGUI gui, User user) {
        BasicWindow showProfileWindow = new BasicWindow("OOP Rentals");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label("Name: "));
        panel.addComponent(new Label(user.getName()));

        panel.addComponent(new Label("Email: "));
        panel.addComponent(new Label(user.getEmail()));

        panel.addComponent(new Label("Phone Number: "));
        panel.addComponent(new Label(user.getPhoneNo()));

        Button logoutButton = new Button("Go Back", () -> {
            showProfileWindow.close();
            showLoggedMenu(gui, user);
        });

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(new EmptySpace());
        panel.addComponent(logoutButton);

        showProfileWindow.setComponent(panel);
        gui.addWindowAndWait(showProfileWindow);
    }
}
