package org.example.CarMgmt;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;

import org.example.CarMgmt.menus.preLogin.MainMenu;

public class App 
{;
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
