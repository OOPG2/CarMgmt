package menus;

import app.*;
import constants.*;
import manager.*;
import objects.*;
import static helper.Flash.flash;
import static menus.LoggedMenu.showLoggedMenu;
import static menus.preLogin.LoginMenu.showLoginMenu;

import com.googlecode.lanterna.gui2.*;


public class ChangePasswordMenu {

    public static void showChangePasswordMenu(MultiWindowTextGUI gui) {
        App app = new App();

        UserManager userManager = app.getUserManager();
        AuthenticationManager authenticationManager = app.getAuthenticationManager();
        Constants constants = app.getConstants();
        User loggedUser = authenticationManager.getLoggedUser();

        BasicWindow showChangePasswordWindow = new BasicWindow("OOP Rentals - Change Password");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(4));

        panel.addComponent(new Label("Password:"));
        final TextBox passwordBox = new TextBox().setMask('*').setLayoutData(GridLayout.createHorizontallyFilledLayoutData(3)).addTo(panel);

        panel.addComponent(new Label("Retype Password:"));
        final TextBox confirmPasswordBox = new TextBox().setMask('*').setLayoutData(GridLayout.createHorizontallyFilledLayoutData(3)).addTo(panel);

        final Label errorMessageLabel = new Label("");

        Button confirmButton = new Button("Confirm", () -> {
            try {
                String password = passwordBox.getText();
                String confirmPassword = confirmPasswordBox.getText();

                if (password.isEmpty()) {
                    errorMessageLabel.setText("Password cannot be empty!");
                    gui.updateScreen();
                }
                else if (password.equals(confirmPassword)) {
                    if (!password.equals(constants.getDefaultPassword())) {
                        if (!password.equals(loggedUser.getPassword())) {
                            showChangePasswordWindow.close();
                            loggedUser.setPassword(password);
                            userManager.updateUser(loggedUser);

                            flash(gui, "Password successfully changed!", 1500);

                            showLoggedMenu();
                        }
                        else {
                            errorMessageLabel.setText("New password cannot be the same as current!");
                            gui.updateScreen();
                        }
                    }
                    else {
                        errorMessageLabel.setText("New password cannot be 'password'!");
                        gui.updateScreen();
                    }
                } else {
                    errorMessageLabel.setText("Password mismatched!");
                    gui.updateScreen();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button returnButton = new Button("Go Back", () -> {
            showChangePasswordWindow.close();

            if (loggedUser.getPassword().equals(constants.getDefaultPassword())) {
                authenticationManager.logoutUser();
                showLoginMenu(gui, loggedUser.getUserId());
            }
            else {
                if (MenuManager.getCameFrom() != null)
                    MenuManager.redirect(gui);
                else
                    showLoggedMenu();
            }
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

        panel.addComponent(confirmButton.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        panel.addComponent(returnButton.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        showChangePasswordWindow.setComponent(panel);
        gui.addWindowAndWait(showChangePasswordWindow);
    }
}
