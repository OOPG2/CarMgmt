package org.example.CarMgmt.menus.preLogin;

import org.example.CarMgmt.helper;
import org.example.CarMgmt.manager.UserManager;

import com.googlecode.lanterna.gui2.*;
import java.util.regex.Pattern;

import static org.example.CarMgmt.menus.preLogin.MainMenu.showMainMenu;

public class createAccountMenu {
    public static void showCreateAccountMenu(MultiWindowTextGUI gui) {
        BasicWindow createAccountWindow = new BasicWindow("OOP Rentals");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label("Username"));
        final TextBox usernameBox = new TextBox().setValidationPattern(Pattern.compile("[a-zA-Z]+")).addTo(panel);

        panel.addComponent(new Label("Password"));
        final TextBox passwordBox = new TextBox().setValidationPattern(Pattern.compile("[a-zA-Z]+")).addTo(panel);

        Button createAccountButton = new Button("Create", () -> {
            try {
                UserManager userManager = new UserManager();
                String username = usernameBox.getText();
                String password = passwordBox.getText();

                userManager.createUser(username, password, "Customer");

                createAccountWindow.close();

                new helper().flash(gui, "Account created!", 1500);

                showMainMenu(gui);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button goBack = new Button("Go Back", () -> {
            createAccountWindow.close();
            showMainMenu(gui);
        });

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(createAccountButton);
        panel.addComponent(goBack);

        createAccountWindow.setComponent(panel);
        gui.addWindowAndWait(createAccountWindow);
    }
}
