package org.example.CarMgmt.menus.preLogin;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import org.example.CarMgmt.App;
import org.example.CarMgmt.Constants;
import org.example.CarMgmt.manager.AuthenticationManager;

import com.googlecode.lanterna.gui2.*;

import static org.example.CarMgmt.helper.Flash.flash;
import static org.example.CarMgmt.menus.postLogin.ChangePasswordMenu.showChangePasswordMenu;
import static org.example.CarMgmt.menus.postLogin.LoggedMenu.showLoggedMenu;
import static org.example.CarMgmt.menus.preLogin.MainMenu.showMainMenu;

public class LoginMenu {
    public static void showLoginMenu(MultiWindowTextGUI gui, String createdId) {
        App app = new App();

        AuthenticationManager authenticationManager = app.getAuthenticationManager();
        Constants constants = app.getConstants();

        BasicWindow loginWindow = new BasicWindow("OOP Rentals");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(4));

        final Label userIdLabel = new Label("User ID:");
        final TextBox userIdBox = new TextBox(createdId).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(3));

        final Label passwordLabel = new Label("Password:");
        final TextBox passwordBox = new TextBox("").setMask('*').setLayoutData(GridLayout.createHorizontallyFilledLayoutData(3));

        final Label errorMessageLabel = new Label("");

        Button loginButton = new Button("Login", () -> {
            try {
                String userId = userIdBox.getText();
                String password = passwordBox.getText();

                if (authenticationManager.isUserBanned(userId)) {
                    errorMessageLabel.setText("User ID is banned!");
                    gui.updateScreen();
                }
                else if (authenticationManager.loginUser(userId, password)) {
                    if (password.equals(constants.getDefaultPassword())) {
                        loginWindow.close();
                        showChangePasswordMenu(gui);
                    }
                    else {
                        loginWindow.close();

                        flash(gui, String.format("Welcome back, %s!", userId), 1000);

                        showLoggedMenu(gui);
                    }
                }
                else {
                    errorMessageLabel.setText("Invalid user ID/password!");
                    gui.updateScreen();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button returnButton = new Button("Go Back", () -> {
            loginWindow.close();
            showMainMenu(gui);
        });

        panel.addComponent(userIdLabel);
        panel.addComponent(userIdBox);

        panel.addComponent(passwordLabel);
        panel.addComponent(passwordBox);

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(errorMessageLabel.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(4)));

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(loginButton.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        panel.addComponent(returnButton.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        loginWindow.setComponent(panel);
        gui.addWindowAndWait(loginWindow);
    }
}
