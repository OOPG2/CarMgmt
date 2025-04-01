package org.example.CarMgmt.menus.preLogin;

import org.example.CarMgmt.helper;
import org.example.CarMgmt.manager.UserManager;

import com.googlecode.lanterna.gui2.*;
import org.example.CarMgmt.objects.User;

import java.util.regex.Pattern;

import static org.example.CarMgmt.menus.postLogin.LoggedMenu.showLoggedMenu;
import static org.example.CarMgmt.menus.preLogin.MainMenu.showMainMenu;

public class LoginMenu {
    public static void showLoginMenu(MultiWindowTextGUI gui) {
        BasicWindow loginWindow = new BasicWindow("OOP Rentals");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label("Username"));
        final TextBox usernameBox = new TextBox().setValidationPattern(Pattern.compile("[a-zA-Z]+")).addTo(panel);

        panel.addComponent(new Label("Password"));
        final TextBox passwordBox = new TextBox().setValidationPattern(Pattern.compile("[a-zA-Z]+")).addTo(panel);

        final Label errorMessageLabel = new Label("");

        Button createAccountButton = new Button("Login", () -> {
            try {
                UserManager userManager = new UserManager();
                String username = usernameBox.getText();
                String password = passwordBox.getText();

                User user = userManager.verifyUser(username, password);

                if (user != null) {
                    loginWindow.close();

                    new helper().flash(gui, String.format("Welcome back, %s!", username), 1500);

                    showLoggedMenu(gui, user);
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
        Button goBack = new Button("Go Back", () -> {
            loginWindow.close();
            showMainMenu(gui);
        });

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(createAccountButton);
        panel.addComponent(goBack);

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(errorMessageLabel);
        panel.addComponent(new EmptySpace());

        loginWindow.setComponent(panel);
        gui.addWindowAndWait(loginWindow);
    }
}
