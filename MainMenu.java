package org.example.crms;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.screen.TerminalScreen;
import java.io.IOException;

/**
 * Main menu class that handles user login and navigation.
 * Allows a user to login (as Customer or Staff) and then opens the appropriate menu options using Lanterna.
 */
public class MainMenu {
    private static User currentUser = null;
    private static boolean exitProgram = false;

    public static void main(String[] args) {
        // Show current working directory for debugging
        System.out.println("Working Directory: " + System.getProperty("user.dir"));

        ReservationManager manager = new ReservationManager();
        Terminal terminal = null;
        Screen screen = null;

        try {
            // ✅ Use terminal emulator mode to fix Windows Lanterna issue
            terminal = new DefaultTerminalFactory()
                    .setPreferTerminalEmulator(true)
                    .createTerminal();


            screen = new TerminalScreen(terminal);
            screen.startScreen();
            MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen);

            while (true) {
                BasicWindow loginWindow = new BasicWindow("Login");
                Panel loginPanel = new Panel(new GridLayout(2));

                loginPanel.addComponent(new Label("Username:"));
                TextBox usernameBox = new TextBox();
                loginPanel.addComponent(usernameBox);

                loginPanel.addComponent(new Label("Password:"));
                TextBox passwordBox = new TextBox();
                loginPanel.addComponent(passwordBox);

                Label loginMessage = new Label(" ");
                loginMessage.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER, true, false, 2, 1));
                loginPanel.addComponent(loginMessage);

                Button loginButton = new Button("Login", () -> {
                    String username = usernameBox.getText().trim();
                    String password = passwordBox.getText().trim();
                    User user = manager.authenticate(username, password);
                    if (user != null) {
                        currentUser = user;
                        loginWindow.close();
                    } else {
                        loginMessage.setText("Invalid credentials, please try again.");
                    }
                });

                Button exitButton = new Button("Exit", () -> {
                    exitProgram = true;
                    loginWindow.close();
                });

                Panel buttonPanel = new Panel(new GridLayout(2));
                buttonPanel.addComponent(loginButton);
                buttonPanel.addComponent(exitButton);
                buttonPanel.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER, true, false, 2, 1));
                loginPanel.addComponent(buttonPanel);

                loginWindow.setComponent(loginPanel);
                textGUI.addWindowAndWait(loginWindow);

                if (exitProgram) break;
                if (currentUser == null) continue;

                // Show menu
                User loggedInUser = currentUser;
                BasicWindow menuWindow = new BasicWindow("Main Menu - " + loggedInUser.getRole());
                Panel menuPanel = new Panel(new LinearLayout(Direction.VERTICAL));
                menuPanel.addComponent(new Label("Welcome, " + loggedInUser.getUsername() + "!"));

                if (loggedInUser.getRole().equalsIgnoreCase("Customer")) {
                    menuPanel.addComponent(new Button("Make a Reservation", () -> {
                        MakeReservationScreen makeScreen = new MakeReservationScreen(manager, loggedInUser);
                        textGUI.addWindowAndWait(makeScreen);
                    }));
                    menuPanel.addComponent(new Button("View My Reservations", () -> {
                        ViewReservationsScreen viewScreen = new ViewReservationsScreen(manager, loggedInUser);
                        textGUI.addWindowAndWait(viewScreen);
                    }));
                    menuPanel.addComponent(new Button("Modify a Reservation", () -> {
                        ModifyReservationScreen modScreen = new ModifyReservationScreen(manager, loggedInUser);
                        textGUI.addWindowAndWait(modScreen);
                    }));
                    menuPanel.addComponent(new Button("Cancel a Reservation", () -> {
                        CancelReservationScreen cancelScreen = new CancelReservationScreen(manager, loggedInUser);
                        textGUI.addWindowAndWait(cancelScreen);
                    }));
                }

                if (loggedInUser.getRole().equalsIgnoreCase("Staff")) {
                    menuPanel.addComponent(new Button("View All Reservations", () -> {
                        ViewReservationsScreen viewScreen = new ViewReservationsScreen(manager, loggedInUser);
                        textGUI.addWindowAndWait(viewScreen);
                    }));
                    menuPanel.addComponent(new Button("Update Reservation Status", () -> {
                        ReservationStatusUpdaterScreen statusScreen = new ReservationStatusUpdaterScreen(manager);
                        textGUI.addWindowAndWait(statusScreen);
                    }));
                }

                menuPanel.addComponent(new Button("Logout", () -> {
                    currentUser = null;
                    menuWindow.close();
                }));
                menuPanel.addComponent(new Button("Exit Program", () -> {
                    exitProgram = true;
                    menuWindow.close();
                }));

                menuWindow.setComponent(menuPanel);
                textGUI.addWindowAndWait(menuWindow);

                if (exitProgram) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (screen != null) {
                try {
                    screen.stopScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
