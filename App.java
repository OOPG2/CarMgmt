package org.example.CarMgmt;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
import java.util.Map;

import org.example.CarMgmt.factories.AdminFactory;
import org.example.CarMgmt.factories.CustomerFactory;
import org.example.CarMgmt.factories.StaffFactory;
import org.example.CarMgmt.factories.UserFactory;
import org.example.CarMgmt.manager.AuthenticationManager;
import org.example.CarMgmt.manager.UserManager;
import org.example.CarMgmt.menus.preLogin.MainMenu;
import org.example.CarMgmt.repositories.UserRepository;

public class App 
{;
    public UserManager userManager = new UserManager(new UserRepository(), createFactories(), new Constants());
    public AuthenticationManager authenticationManager = new AuthenticationManager(userManager);
    public Constants constants = new Constants();

    public UserManager getUserManager() {
        return userManager;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public Constants getConstants() {
        return constants;
    }

    private Map<String, UserFactory> createFactories() {
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
            screen = terminalFactory.createScreen();
            screen.startScreen();

            // Create the GUI based on our screen
            MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);

            // TODO: show BeforeLogin main menu
            MainMenu.showMainMenu(gui);
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
