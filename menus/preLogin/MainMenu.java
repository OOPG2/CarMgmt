package org.example.CarMgmt.menus.preLogin;

import com.googlecode.lanterna.gui2.*;

import static org.example.CarMgmt.menus.preLogin.LoginMenu.showLoginMenu;
import static org.example.CarMgmt.menus.preLogin.createAccountMenu.showCreateAccountMenu;

public class MainMenu {
    public static void showMainMenu(MultiWindowTextGUI gui) {
        BasicWindow mainMenuWindow = new BasicWindow("OOP Rentals");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        Button loginButton = new Button("Login", () -> {
            mainMenuWindow.close();
            showLoginMenu(gui);
        });
        Button createAccountButton = new Button("Create Account", () -> {
            mainMenuWindow.close();
            showCreateAccountMenu(gui);
        });

        Button exitButton = new Button("Exit", mainMenuWindow::close);

        panel.addComponent(loginButton);
        panel.addComponent(createAccountButton);
        panel.addComponent(new EmptySpace());
        panel.addComponent(exitButton);
        mainMenuWindow.setComponent(panel);
        gui.addWindowAndWait(mainMenuWindow);
    }
}
