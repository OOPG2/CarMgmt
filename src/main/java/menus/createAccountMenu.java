package menus;

import app.App;
import com.googlecode.lanterna.gui2.*;
import helper.IDGenerator;
import manager.AuthenticationManager;
import manager.MenuManager;
import manager.UserManager;
import objects.User;

import java.util.regex.Pattern;

import static helper.Flash.flash;
import static menus.postLogin.Admin.StaffManagementMenu.showStaffManagementMenu;
import static menus.preLogin.LoginMenu.showLoginMenu;
import static menus.preLogin.MainMenu.showMainMenu;

public class createAccountMenu {
    public static void showCreateAccountMenu(MultiWindowTextGUI gui) {
        App app = new App();

        UserManager userManager = app.getUserManager();
        AuthenticationManager authenticationManager = app.getAuthenticationManager();
        User loggedUser = authenticationManager.getLoggedUser();

        BasicWindow createAccountWindow = new BasicWindow("OOP Rentals - Create Account");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(4));

        final Label userIdLabel = new Label("");
        final Label errorMessageLabel = new Label("");

        panel.addComponent(new Label("Name:").setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        panel.addComponent(new Label("Email:").setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        final TextBox nameBox = new TextBox().setValidationPattern(Pattern.compile("[a-zA-Z ]*"));
        nameBox.setTextChangeListener((name, changedByUserInteraction) -> {
            if (changedByUserInteraction) {
                String processedName = name;

                // 1. Remove leading space if present
                if (!processedName.isEmpty() && processedName.charAt(0) == ' ') {
                    processedName = processedName.substring(1);
                }

                // 2. Enforce 50-character limit
                if (processedName.length() > 50) {
                    processedName = processedName.substring(0, 50);
                }

                // 3. Update textbox if corrections were made
                if (!processedName.equals(name)) {
                    nameBox.setText(processedName);
                    processedName = nameBox.getText(); // Get the final sanitized value
                }

                // 4. Check if name is empty OR contains only spaces
                if (processedName.trim().isEmpty()) {
                    userIdLabel.setText("");  // Clear ID display
                    return; // Exit early - don't generate ID for empty/whitespace
                }

                // 5. Generate user ID for valid names
                String userId;
                do {
                    userId = IDGenerator.generateUniqueID(
                            processedName.toLowerCase().contains(" ")
                                    ? processedName.substring(0, processedName.indexOf(" ")).toLowerCase()
                                    : processedName.toLowerCase()
                    );
                } while (userManager.getUserByID(userId) != null);

                userIdLabel.setText(userId);
            }
        });
        panel.addComponent(nameBox.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        final TextBox emailBox = new TextBox().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)).addTo(panel);

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(new Label("User ID:").setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        panel.addComponent(new Label("Phone Number:").setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        panel.addComponent(userIdLabel.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        final TextBox phoneBox = new TextBox().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)).addTo(panel);

        Button createAccountButton = new Button("Create");
        if (loggedUser == null) {
            createAccountButton.addListener(button -> {
                try {
                    String name = nameBox.getText();
                    String userId = userIdLabel.getText();
                    String email = emailBox.getText();
                    String phone = phoneBox.getText();

                    if (name.isEmpty()) {
                        errorMessageLabel.setText("Name field cannot be empty!");
                        gui.updateScreen();
                    }
                    else if (userId.isEmpty()) {
                        errorMessageLabel.setText("User ID field cannot be empty! System error.");
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
                        userManager.createUser(userId, name, email, phone, "Customer");

                        createAccountWindow.close();
                        flash(gui, String.format("Account '%s' created!", userId), 1500);
                        showLoginMenu(gui, userId);
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
                    String userId = userIdLabel.getText();
                    String email = emailBox.getText();
                    String phone = phoneBox.getText();

                    if (name.isEmpty()) {
                        errorMessageLabel.setText("Name field cannot be empty!");
                        gui.updateScreen();
                    }
                    else if (userId.isEmpty()) {
                        errorMessageLabel.setText("User ID field cannot be empty! System error.");
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
                        userManager.createUser(userId, name, email, phone, "Staff");

                        createAccountWindow.close();
                        flash(gui, String.format("Staff '%s' created!", userId), 1500);
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
