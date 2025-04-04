package org.example.CarMgmt.menus.postLogin;

import org.example.CarMgmt.helper;
import org.example.CarMgmt.manager.AuthenticationManager;
import org.example.CarMgmt.manager.MenuManager;
import org.example.CarMgmt.manager.UserManager;
import org.example.CarMgmt.objects.User;

import com.googlecode.lanterna.gui2.*;

import static org.example.CarMgmt.menus.postLogin.LoggedMenu.showLoggedMenu;
import static org.example.CarMgmt.menus.preLogin.LoginMenu.showLoginMenu;

public class ChangePasswordMenu {

    public static void showChangePasswordMenu(MultiWindowTextGUI gui) {
        User loggedUser = AuthenticationManager.getLoggedUser();

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
                    if (!password.equals(AuthenticationManager.defaultPassword)) {
                        showChangePasswordWindow.close();
                        loggedUser.setPassword(password);
                        UserManager.updateUser(loggedUser);

                        helper.flash(gui, "Password successfully changed!", 1500);

                        showLoggedMenu(gui);
                    }
                    else {
                        errorMessageLabel.setText("New password cannot be the same as the current!");
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

            if (loggedUser.getPassword().equals(AuthenticationManager.defaultPassword)) {
                AuthenticationManager.logoutUser();
                showLoginMenu(gui);
            }
            else {
                if (MenuManager.getCameFrom() != null)
                    MenuManager.redirect(gui);
                else
                    showLoggedMenu(gui);
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
