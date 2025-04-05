package org.example.CarMgmt.menus.preLogin;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import org.example.CarMgmt.helper;
import org.example.CarMgmt.manager.AuthenticationManager;

import com.googlecode.lanterna.gui2.*;

import static org.example.CarMgmt.menus.postLogin.ChangePasswordMenu.showChangePasswordMenu;
import static org.example.CarMgmt.menus.postLogin.LoggedMenu.showLoggedMenu;
import static org.example.CarMgmt.menus.preLogin.MainMenu.showMainMenu;

public class LoginMenu {
    public static void showLoginMenu(MultiWindowTextGUI gui) {
        BasicWindow loginWindow = new BasicWindow("OOP Rentals");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(4));

        final Label userIdLabel = new Label("User ID:");
        final TextBox userIdBox = new TextBox().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(3));

        final Label passwordLabel = new Label("Password:");
        final TextBox passwordBox = new TextBox().setMask('*').setLayoutData(GridLayout.createHorizontallyFilledLayoutData(3));

        final Label errorMessageLabel = new Label("");

        Button createAccountButton = new Button("Login", () -> {
            try {
                String userId = userIdBox.getText();
                String password = passwordBox.getText();

                if (AuthenticationManager.isUserBanned(userId)) {
                    errorMessageLabel.setText("User ID is banned!");
                    gui.updateScreen();
                }
                else if (AuthenticationManager.loginUser(userId, password)) {
                    if (password.equals(AuthenticationManager.defaultPassword)) {
                        loginWindow.close();
                        showChangePasswordMenu(gui);
                    }
                    else {
                        loginWindow.close();

                        helper.flash(gui, String.format("Welcome back, %s!", userId), 1000);

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

        panel.addComponent(createAccountButton.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        panel.addComponent(returnButton.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        loginWindow.setComponent(panel);
        gui.addWindowAndWait(loginWindow);
    }
}
