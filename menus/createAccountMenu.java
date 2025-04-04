package org.example.CarMgmt.menus;

import org.example.CarMgmt.helper;
import org.example.CarMgmt.manager.AuthenticationManager;
import org.example.CarMgmt.manager.MenuManager;
import org.example.CarMgmt.manager.UserManager;

import com.googlecode.lanterna.gui2.*;
import org.example.CarMgmt.objects.User;

import static org.example.CarMgmt.menus.postLogin.Admin.StaffManagementMenu.showStaffManagementMenu;
import static org.example.CarMgmt.menus.preLogin.MainMenu.showMainMenu;

public class createAccountMenu {
    public static void showCreateAccountMenu(MultiWindowTextGUI gui) {
        User loggedUser = AuthenticationManager.getLoggedUser();

        BasicWindow createAccountWindow = new BasicWindow("OOP Rentals - Create Account");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(4));

        final Label errorMessageLabel = new Label("");

        panel.addComponent(new Label("Name:").setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        panel.addComponent(new Label("Username:").setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        final TextBox nameBox = new TextBox().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)).addTo(panel);
        final TextBox usernameBox = new TextBox().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)).addTo(panel);

        panel.addComponent(new Label("Email:").setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        panel.addComponent(new Label("Phone Number:").setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        final TextBox emailBox = new TextBox().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)).addTo(panel);
        final TextBox phoneBox = new TextBox().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)).addTo(panel);

        Button createAccountButton = new Button("Create");
        if (loggedUser == null) {
            createAccountButton.addListener(button -> {
                try {
                    String name = nameBox.getText();
                    String username = usernameBox.getText();
                    String email = emailBox.getText();
                    String phone = phoneBox.getText();

                    if (name.isEmpty()) {
                        errorMessageLabel.setText("Name field cannot be empty!");
                        gui.updateScreen();
                    }
                    else if (username.isEmpty()) {
                        errorMessageLabel.setText("Username field cannot be empty!");
                        gui.updateScreen();
                    }
                    else if (email.isEmpty()) {
                        errorMessageLabel.setText("Email field cannot be empty!");
                        gui.updateScreen();
                    }
                    else if (phone.isEmpty()) {
                        errorMessageLabel.setText("Phone Number field cannot be empty!");
                        gui.updateScreen();
                    }
                    else if (!email.matches(".+@.+\\..+")) {
                        errorMessageLabel.setText("Email format is incorrect!");
                        gui.updateScreen();
                    }
                    else if (!phone.matches("^[0-9]{8}$")) {
                        errorMessageLabel.setText("Phone number must be 8 numeric digit (e.g. 87654321)!");
                        gui.updateScreen();
                    }
                    else {
                        UserManager.createUser(username, name, email, phone, "Customer");

                        createAccountWindow.close();
                        helper.flash(gui, "Account created!", 1500);
                        showMainMenu(gui);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        else if (loggedUser.getRole().equals("Admin") && MenuManager.getCameFrom().equals("StaffManagement")) {
            createAccountWindow.setTitle("OOP Rentals - Create Staff");
            createAccountButton.addListener(button -> {
                try {
                    String name = nameBox.getText();
                    String username = usernameBox.getText();
                    String email = emailBox.getText();
                    String phone = phoneBox.getText();

                    if (name.isEmpty()) {
                        errorMessageLabel.setText("Name field cannot be empty!");
                        gui.updateScreen();
                    }
                    else if (username.isEmpty()) {
                        errorMessageLabel.setText("Username field cannot be empty!");
                        gui.updateScreen();
                    }
                    else if (email.isEmpty()) {
                        errorMessageLabel.setText("Email field cannot be empty!");
                        gui.updateScreen();
                    }
                    else if (phone.isEmpty()) {
                        errorMessageLabel.setText("Phone Number field cannot be empty!");
                        gui.updateScreen();
                    }
                    else if (!email.matches(".+@.+\\..+")) {
                        errorMessageLabel.setText("Email format is incorrect!");
                        gui.updateScreen();
                    }
                    else if (!phone.matches("^[0-9]{8}$")) {
                        errorMessageLabel.setText("Phone number must be 8 numeric digit (e.g. 87654321)!");
                        gui.updateScreen();
                    }
                    else {
                        UserManager.createUser(username, name, email, phone, "Staff");

                        createAccountWindow.close();
                        helper.flash(gui, "Staff created!", 1500);
                        showStaffManagementMenu(gui);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        Button returnButton = new Button("Go Back", () -> {
            createAccountWindow.close();
            if (MenuManager.getCameFrom() != null)
                MenuManager.redirect(gui);
            else
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

        createAccountWindow.setComponent(panel);
        gui.addWindowAndWait(createAccountWindow);
    }
}
