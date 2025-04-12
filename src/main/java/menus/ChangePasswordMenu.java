package menus;

import app.App;
import com.googlecode.lanterna.gui2.*;
import constants.Constants;
import manager.AuthenticationManager;
import manager.MenuManager;
import manager.UserManager;
import objects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static helper.Flash.flash;
import static menus.LoggedMenu.showLoggedMenu;
import static menus.preLogin.LoginMenu.showLoginMenu;


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
                    errorMessageLabel.setText("New password cannot be empty!");
                    gui.updateScreen();
                } else {
                    List<String> errors = checkPasswordComplexity(password);
                    if (!errors.isEmpty()) {
                        StringBuilder errorMessage = new StringBuilder("Password must:\n");
                        for (String error : errors) {
                            errorMessage.append("- ").append(error).append("\n");
                        }
                        errorMessageLabel.setText(errorMessage.toString());
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
                            errorMessageLabel.setText("New password cannot be '" + constants.getDefaultPassword() + "'!");
                            gui.updateScreen();
                        }
                    } else {
                        errorMessageLabel.setText("Password mismatched!");
                        gui.updateScreen();
                    }
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

    private static List<String> checkPasswordComplexity(String password) {
        List<String> errors = new ArrayList<>();

        if (password.length() < 8) {
            errors.add("At least 8 characters long");
        }

        // At least one uppercase letter
        Pattern uppercase = Pattern.compile("[A-Z]");
        Matcher hasUppercase = uppercase.matcher(password);
        if (!hasUppercase.find()) {
            errors.add("Contain at least 1 uppercase letter");
        }

        // At least one lowercase letter
        Pattern lowercase = Pattern.compile("[a-z]");
        Matcher hasLowercase = lowercase.matcher(password);
        if (!hasLowercase.find()) {
            errors.add("Contain at least 1 lowercase letter");
        }

        // At least one digit
        Pattern digit = Pattern.compile("[0-9]");
        Matcher hasDigit = digit.matcher(password);
        if (!hasDigit.find()) {
            errors.add("Contain at least 1 number");
        }

        return errors;
    }
}
