package org.example;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Screen screen = null;
        try {
            screen = terminalFactory.createScreen();
            screen.startScreen();

            // Create the GUI based on our screen
            MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);

            // TODO: show BeforeLogin main menu
            //StaffVehicleManagementMenu.showMenu(gui);
            CustomerVehicleManagementMenu.showMenu(gui);
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