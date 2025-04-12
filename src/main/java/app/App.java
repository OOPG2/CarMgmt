package app;

import Reservations.ReservationManager;
import factories.*;
import manager.*;
import menus.preLogin.*;
import constants.*;
import exceptions.DatabaseErrorHandler;
import repositories.*;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Map;

public class App 
{
	public static MultiWindowTextGUI gui;
    public static ReservationManager reservationManager = new ReservationManager();
    public static UserManager userManager = new UserManager(new UserRepository(), createFactories(), new Constants());
    public static AuthenticationManager authenticationManager = new AuthenticationManager(userManager);
    public Constants constants = new Constants();

    public UserManager getUserManager() {
        return userManager;
    }

    public static AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public Constants getConstants() {
        return constants;
    }

    private static Map<String, UserFactory> createFactories() {
        return Map.of(
                "Customer", new CustomerFactory(),
                "Staff", new StaffFactory(),
                "Admin", new AdminFactory()
                // Add new roles here (e.g., "Manager", new ManagerFactory())
        );
    }

	public static void main(String[] args) {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Screen screen = null;
        try {
            screen = new TerminalScreen(terminalFactory.createTerminalEmulator()); //terminalFactory.createScreen();
            screen.startScreen();

            // Create the GUI based on our screen
            gui = new MultiWindowTextGUI(screen);

            // TODO: show BeforeLogin main menu
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
