package org.example.CarMgmt.menus.postLogin;

import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import org.example.CarMgmt.helper;
import org.example.CarMgmt.manager.AuthenticationManager;
import org.example.CarMgmt.manager.MenuManager;
import org.example.CarMgmt.manager.UserManager;
import org.example.CarMgmt.objects.User;

import com.googlecode.lanterna.gui2.*;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.example.CarMgmt.menus.postLogin.ChangePasswordMenu.showChangePasswordMenu;
import static org.example.CarMgmt.menus.postLogin.LoggedMenu.showLoggedMenu;

public class ProfileMenu {

    public static void showProfileMenu(MultiWindowTextGUI gui, User viewingUser) {
        User loggedUser = AuthenticationManager.getLoggedUser();
        AtomicBoolean editingProfile = new AtomicBoolean(false);

        BasicWindow showProfileWindow = new BasicWindow();
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));
        LayoutData textBoxLayout = GridLayout.createHorizontallyFilledLayoutData();

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

        Button banCustomerButton = new Button("Ban Customer", () -> {
            MessageDialogButton dialogResult = new MessageDialogBuilder()
                    .setTitle("Confirm Ban Customer")
                    .setText("Are you sure?")
                    .addButton(MessageDialogButton.No)
                    .addButton(MessageDialogButton.Yes)
                    .build()
                    .showDialog(gui);

            if (dialogResult.equals(MessageDialogButton.Yes) && viewingUser != null) {

                viewingUser.setIsBanned(true);

                UserManager.updateUser(viewingUser);
                showProfileWindow.close();
                if (MenuManager.getCameFrom() != null)
                    MenuManager.redirect(gui);
                else
                    showLoggedMenu(gui);
            }
        });

        Button deleteStaffButton = new Button("Delete Staff", () -> {
            MessageDialogButton dialogResult = new MessageDialogBuilder()
                    .setTitle("Confirm Delete Staff")
                    .setText("Warning: This action is irreversible!")
                    .addButton(MessageDialogButton.No)
                    .addButton(MessageDialogButton.Yes)
                    .build()
                    .showDialog(gui);

            if (dialogResult.equals(MessageDialogButton.Yes) && viewingUser != null) {
                UserManager.deleteUser(viewingUser.getUserId());
                showProfileWindow.close();
                if (MenuManager.getCameFrom() != null)
                    MenuManager.redirect(gui);
                else
                    showLoggedMenu(gui);
            }
        });

        TextBox nameBox = new TextBox(loggedUser.getName()).setReadOnly(true);
        TextBox emailBox = new TextBox(loggedUser.getEmail()).setReadOnly(true);
        TextBox phoneBox = new TextBox(loggedUser.getPhone()).setReadOnly(true);

        panel.addComponent(new Label("Name: "));
        panel.addComponent(nameBox.setLayoutData(textBoxLayout));

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(new Label("Email: "));
        panel.addComponent(emailBox.setLayoutData(textBoxLayout));

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(new Label("Phone Number: "));
        panel.addComponent(phoneBox.setLayoutData(textBoxLayout));

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
                    helper.flash(gui, "Edit Profile Mode", 1000);
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
                if (changePasswordButton != null)
                    changePasswordButton.setVisible(true);
                deleteStaffButton.setVisible(true);
                editingProfile.set(false);
                showProfileWindow.setTitle("OOP Rentals - Profile");
                editProfileButton.setLabel("Edit Profile");
                try {
                    helper.flash(gui, "Profile saved successfully!", 1000);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                nameBox.setReadOnly(true);
                emailBox.setReadOnly(true);
                phoneBox.setReadOnly(true);

                editedUser.setName(nameBox.getText());
                editedUser.setEmail(emailBox.getText());
                editedUser.setPhone(phoneBox.getText());

                UserManager.updateUser(editedUser);
                if (viewingUser == null)
                    AuthenticationManager.setLoggedUser(editedUser);

                try {
                    gui.updateScreen();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Button returnButton = new Button("Go Back", () -> {
            showProfileWindow.close();
            if (MenuManager.getCameFrom() != null)
                MenuManager.redirect(gui);
            else
                showLoggedMenu(gui);
        });

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        if (viewingUser != null) {
            switch (viewingUser.getRole()) {
                case "Customer" -> {
                    panel.addComponent(new EmptySpace());
                    panel.addComponent(new EmptySpace());
                    if (viewingUser.getIsBanned())
                        panel.addComponent(new EmptySpace());
                    else
                        panel.addComponent(banCustomerButton);
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
            panel.addComponent((changePasswordButton));
            panel.addComponent(new EmptySpace());
            panel.addComponent(editProfileButton);
        }
        panel.addComponent(returnButton.setLayoutData(GridLayout.createHorizontallyEndAlignedLayoutData(1)));

        showProfileWindow.setComponent(panel);
        gui.addWindowAndWait(showProfileWindow);
    }
}
