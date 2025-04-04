package org.example.CarMgmt.menus.preLogin;

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

        panel.addComponent(new Label("Username:"));
        final TextBox usernameBox = new TextBox().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(3)).addTo(panel);

        panel.addComponent(new Label("Password:"));
        final TextBox passwordBox = new TextBox().setMask('*').setLayoutData(GridLayout.createHorizontallyFilledLayoutData(3)).addTo(panel);

        final Label errorMessageLabel = new Label("");

        Button createAccountButton = new Button("Login", () -> {
            try {
                String username = usernameBox.getText();
                String password = passwordBox.getText();

                if (AuthenticationManager.loginUser(username, password)) {
                    if (password.equals(AuthenticationManager.defaultPassword)) {
                        loginWindow.close();
                        showChangePasswordMenu(gui);
                    }
                    else {
                        loginWindow.close();

                        helper.flash(gui, String.format("Welcome back, %s!", username), 1500);

                        showLoggedMenu(gui);
                    }
                }
                else {
                    errorMessageLabel.setText("Invalid username/password!");
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
