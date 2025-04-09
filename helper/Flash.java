package org.example.CarMgmt.helper;

import com.googlecode.lanterna.gui2.*;

import java.io.IOException;

public class Flash {
    public static void flash(MultiWindowTextGUI gui, String message, int milliseconds) throws IOException, InterruptedException {
        BasicWindow completeWindow = new BasicWindow();
        Panel completePanel = new Panel();
        completePanel.setLayoutManager(new GridLayout(1));
        completePanel.addComponent(new Label(message));
        completeWindow.setComponent(completePanel);
        gui.addWindow(completeWindow);
        gui.updateScreen();
        Thread.sleep(milliseconds);

        completeWindow.close();
    }
}
