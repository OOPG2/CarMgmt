package org.example.CarMgmt.menus;

import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import org.example.CarMgmt.App;
import org.example.CarMgmt.manager.AuthenticationManager;
import org.example.CarMgmt.manager.MenuManager;
import org.example.CarMgmt.manager.UserManager;
import org.example.CarMgmt.objects.Customer;
import org.example.CarMgmt.objects.User;

import com.googlecode.lanterna.gui2.*;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.example.CarMgmt.helper.Flash.flash;
import static org.example.CarMgmt.menus.ChangePasswordMenu.showChangePasswordMenu;
import static org.example.CarMgmt.menus.LoggedMenu.showLoggedMenu;

public class ProfileMenu {

    public static void showProfileMenu(MultiWindowTextGUI gui, User viewingUser) {
        App app = new App();

        UserManager userManager = app.getUserManager();
        AuthenticationManager authenticationManager = app.getAuthenticationManager();
        User loggedUser = authenticationManager.getLoggedUser();
        AtomicBoolean editingProfile = new AtomicBoolean(false);

        BasicWindow showProfileWindow = new BasicWindow();
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));
        LayoutData infoLayout = GridLayout.createHorizontallyFilledLayoutData();

        final Label errorMessageLabel = new Label("");
        Button changePasswordButton;
        if (viewingUser == null) {
            showProfileWindow.setTitle("OOP Rentals - Profile");
            changePasswordButton = new Button("Change Password", () -> {
                showProfileWindow.close();
                MenuManager.setCameFrom("Profile");
                showChangePasswordMenu(gui);
            });
        }
        else {
            changePasswordButton = null;
            showProfileWindow.setTitle("OOP Rentals - Viewing User");
            loggedUser = viewingUser;
        }

        Button banCustomerButton = new Button("", () -> {

            try {
                if (viewingUser instanceof Customer) {
                    MessageDialogButton dialogResult = new MessageDialogBuilder()
                            .setTitle(((Customer) viewingUser).getIsBanned() ? "Confirm Unban Customer" : "Confirm Ban Customer")
                            .setText("Are you sure?")
                            .addButton(MessageDialogButton.No)
                            .addButton(MessageDialogButton.Yes)
                            .build()
                            .showDialog(gui);

                    if (dialogResult.equals(MessageDialogButton.Yes)) {
                        ((Customer) viewingUser).setIsBanned(!((Customer) viewingUser).getIsBanned());

                        userManager.updateUser(viewingUser);
                        showProfileWindow.close();
                        flash(gui, String.format("Successfully %s %s!", ((Customer) viewingUser).getIsBanned() ? "banned" : "unbanned", viewingUser.getUserId()), 1500);
                        if (MenuManager.getCameFrom() != null)
                            MenuManager.redirect(gui);
                        else
                            showLoggedMenu(gui);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Button deleteStaffButton = new Button("Delete Staff", () -> {
            try {
                MessageDialogButton dialogResult = new MessageDialogBuilder()
                        .setTitle("Confirm Delete Staff")
                        .setText("Warning: This action is irreversible!")
                        .addButton(MessageDialogButton.No)
                        .addButton(MessageDialogButton.Yes)
                        .build()
                        .showDialog(gui);

                if (dialogResult.equals(MessageDialogButton.Yes) && viewingUser != null) {
                    userManager.deleteUser(viewingUser.getUserId());
                    showProfileWindow.close();
                    flash(gui, String.format("Successfully deleted %s!", viewingUser.getUserId()), 1500);
                    if (MenuManager.getCameFrom() != null)
                        MenuManager.redirect(gui);
                    else
                        showLoggedMenu(gui);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        TextBox nameBox = new TextBox(loggedUser.getName()).setReadOnly(true);
        TextBox emailBox = new TextBox(loggedUser.getEmail()).setReadOnly(true);
        TextBox phoneBox = new TextBox(loggedUser.getPhone()).setReadOnly(true);
        Label userIdLabel = new Label(loggedUser.getUserId());

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(new Label("User ID: "));
        panel.addComponent(userIdLabel.setLayoutData(infoLayout));

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(new Label("Name: "));
        panel.addComponent(nameBox.setLayoutData(infoLayout));

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(new Label("Email: "));
        panel.addComponent(emailBox.setLayoutData(infoLayout));

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(new Label("Phone Number: "));
        panel.addComponent(phoneBox.setLayoutData(infoLayout));

        User editedUser = loggedUser;
        Button editProfileButton = new Button("Edit Profile");
        editProfileButton.addListener(button -> {
            if (!editingProfile.get()) {
                if (changePasswordButton != null)
                    changePasswordButton.setVisible(false);
                deleteStaffButton.setVisible(false);
                editingProfile.set(true);
                showProfileWindow.setTitle("OOP Rentals - Editing Profile");
                editProfileButton.setLabel("Save Changes");
                try {
                    flash(gui, "Edit Profile Mode", 1000);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                nameBox.setReadOnly(false);
                emailBox.setReadOnly(false);
                phoneBox.setReadOnly(false);
                try {
                    gui.updateScreen();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                try {
                    String name = nameBox.getText();
                    String email = emailBox.getText();
                    String phone = phoneBox.getText();

                    if (name.isEmpty()) {
                        errorMessageLabel.setText("Name field cannot be empty!");
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
                        if (changePasswordButton != null)
                            changePasswordButton.setVisible(true);
                        deleteStaffButton.setVisible(true);
                        editingProfile.set(false);
                        showProfileWindow.setTitle("OOP Rentals - Profile");
                        editProfileButton.setLabel("Edit Profile");

                        errorMessageLabel.setText("");
                        flash(gui, "Profile saved successfully!", 1000);

                        nameBox.setReadOnly(true);
                        emailBox.setReadOnly(true);
                        phoneBox.setReadOnly(true);

                        editedUser.setName(nameBox.getText());
                        editedUser.setEmail(emailBox.getText());
                        editedUser.setPhone(phoneBox.getText());

                        userManager.updateUser(editedUser);
                        if (viewingUser == null)
                            authenticationManager.setLoggedUser(editedUser);

                        gui.updateScreen();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        User finalLoggedUser = loggedUser;
        Button returnButton = new Button("Go Back", () -> {
            if (!editingProfile.get()) {
                showProfileWindow.close();
                if (MenuManager.getCameFrom() != null)
                    MenuManager.redirect(gui);
                else
                    showLoggedMenu(gui);
            }
            else {
                MessageDialogButton dialogResult = new MessageDialogBuilder()
                        .setTitle("Leaving Edit Profile")
                        .setText("Warning: Changes will be unsaved.")
                        .addButton(MessageDialogButton.No)
                        .addButton(MessageDialogButton.Yes)
                        .build()
                        .showDialog(gui);

                if (dialogResult.equals(MessageDialogButton.Yes)) {
                    if (changePasswordButton != null)
                        changePasswordButton.setVisible(true);
                    deleteStaffButton.setVisible(true);
                    editingProfile.set(false);
                    showProfileWindow.setTitle("OOP Rentals - Profile");
                    editProfileButton.setLabel("Edit Profile");

                    nameBox.setText(finalLoggedUser.getName()).setReadOnly(true);
                    emailBox.setText(finalLoggedUser.getEmail()).setReadOnly(true);
                    phoneBox .setText(finalLoggedUser.getPhone()).setReadOnly(true);
                    userIdLabel.setText(finalLoggedUser.getUserId());
                }
            }
        });

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(errorMessageLabel.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        if (viewingUser != null) {
            switch (viewingUser.getRole()) {
                case "Customer" -> {
                    panel.addComponent(new EmptySpace());
                    panel.addComponent(new EmptySpace());
                    if (((Customer) viewingUser).getIsBanned()) {
                        banCustomerButton.setLabel("Unban Customer");
                        panel.addComponent(banCustomerButton);
                    }
                    else{
                        banCustomerButton.setLabel("Ban Customer");
                        panel.addComponent(banCustomerButton);
                    }
                }
                case "Staff" -> {
                    panel.addComponent(deleteStaffButton);
                    panel.addComponent(new EmptySpace());
                    panel.addComponent(editProfileButton);
                }
                default -> {
                    panel.addComponent(new EmptySpace());
                    panel.addComponent(new EmptySpace());
                }
            }
        }
        else {
            panel.addComponent(changePasswordButton);
            panel.addComponent(new EmptySpace());
            panel.addComponent(editProfileButton);
        }
        panel.addComponent(returnButton.setLayoutData(GridLayout.createHorizontallyEndAlignedLayoutData(1)));

        showProfileWindow.setComponent(panel);
        gui.addWindowAndWait(showProfileWindow);
    }
}
