/**
 * The App class serves as the entry point for the application.
 * It initializes the GUI, sets up required managers and factories, and displays the main menu.
 */
package app;

import Reservations.ReservationManager;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import constants.Constants;
import exceptions.DatabaseErrorHandler;
import factories.AdminFactory;
import factories.CustomerFactory;
import factories.StaffFactory;
import factories.UserFactory;
import manager.AuthenticationManager;
import manager.UserManager;
import menus.preLogin.MainMenu;
import repositories.UserRepository;

import java.io.IOException;
import java.util.Map;

/**
 * App initializes the application, configures the GUI, and handles the program's entry point.
 */
public class App {
    /**
     * The MultiWindowTextGUI instance used to manage the graphical user interface.
     */
    public static MultiWindowTextGUI gui;

    /**
     * The ReservationManager instance for handling reservations.
     */
    public static ReservationManager reservationManager = new ReservationManager();

    /**
     * The UserManager instance for managing user-related data and operations.
     */
    public UserManager userManager = new UserManager(new UserRepository(), createFactories(), new Constants());

    /**
     * The AuthenticationManager instance for handling authentication processes.
     */
    public AuthenticationManager authenticationManager = new AuthenticationManager(userManager);

    /**
     * The Constants instance used for default values and configurations.
     */
    public Constants constants = new Constants();

    /**
     * Returns the UserManager instance.
     *
     * @return the UserManager instance
     */
    public UserManager getUserManager() {
        return userManager;
    }

    /**
     * Returns the AuthenticationManager instance.
     *
     * @return the AuthenticationManager instance
     */
    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    /**
     * Returns the Constants instance.
     *
     * @return the Constants instance
     */
    public Constants getConstants() {
        return constants;
    }

    /**
     * Creates and returns a map of user roles to corresponding UserFactory implementations.
     *
     * @return a map of user roles to UserFactory instances
     */
    private Map<String, UserFactory> createFactories() {
        return Map.of(
                "Customer", new CustomerFactory(),
                "Staff", new StaffFactory(),
                "Admin", new AdminFactory()
                // Add new roles here (e.g., "Manager", new ManagerFactory())
        );
    }

    /**
     * The main method serves as the entry point for the application.
     * It initializes the GUI and displays the main menu.
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Screen screen = null;
        try {
            screen = new TerminalScreen(terminalFactory.createTerminalEmulator()); // terminalFactory.createScreen();
            screen.startScreen();

            // Create the GUI based on the screen
            gui = new MultiWindowTextGUI(screen);

            // Display the main menu
            MainMenu.showMainMenu(gui);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            DatabaseErrorHandler.showDatabaseErrorDialog();
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
