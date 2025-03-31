package org.example.CarMgmt.BeforeLogin;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;

public class MainMenu {
	public static void showMainMenu(MultiWindowTextGUI gui) {
        BasicWindow mainMenuWindow = new BasicWindow("OOP Rentals");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        Button loginButton = new Button("Login", () -> {
            mainMenuWindow.close();
            // TODO: show Login window
        });
        Button createAccountButton = new Button("Create Account", () -> {
            mainMenuWindow.close();
            // TODO: show CreateAccount window
        });

        Button exitButton = new Button("Exit", mainMenuWindow::close);

        panel.addComponent(loginButton);
        panel.addComponent(createAccountButton);
        panel.addComponent(new EmptySpace());
        panel.addComponent(exitButton);
        mainMenuWindow.setComponent(panel);
        gui.addWindowAndWait(mainMenuWindow);
    }
}
