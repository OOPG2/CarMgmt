/**
 * The MainMenu class represents the main menu of the application.
 * It provides navigation options to log in, create an account, or exit the application.
 */
package menus.preLogin;

import com.googlecode.lanterna.gui2.*;

import static menus.createAccountMenu.showCreateAccountMenu;
import static menus.preLogin.LoginMenu.showLoginMenu;

/**
 * MainMenu handles the display and functionality of the main menu using a graphical user interface (GUI).
 */
public class MainMenu {
    /**
     * Displays the main menu and provides navigation options.
     *
     * @param gui the MultiWindowTextGUI object used for displaying the main menu
     */
    public static void showMainMenu(MultiWindowTextGUI gui) {
        BasicWindow mainMenuWindow = new BasicWindow("OOP Rentals");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        // Login button with an action to show the login menu
        Button loginButton = new Button("Login", () -> {
            mainMenuWindow.close();
            showLoginMenu(gui, "");
        });

        // Create Account button with an action to show the account creation menu
        Button createAccountButton = new Button("Create Account", () -> {
            mainMenuWindow.close();
            showCreateAccountMenu(gui);
        });

        // Exit button to close the main menu window
        Button exitButton = new Button("Exit", mainMenuWindow::close);

        // Adding components to the panel
        panel.addComponent(loginButton);
        panel.addComponent(createAccountButton);
        panel.addComponent(new EmptySpace());
        panel.addComponent(exitButton);

        // Setting the panel to the main menu window and displaying it
        mainMenuWindow.setComponent(panel);
        gui.addWindowAndWait(mainMenuWindow);
    }
}
