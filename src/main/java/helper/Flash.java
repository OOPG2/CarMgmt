/**
 * The Flash class provides a utility method for displaying temporary messages in a graphical user interface.
 * It uses the Lanterna library to create a popup window with a message that disappears after a specified duration.
 */
package helper;

import com.googlecode.lanterna.gui2.*;

import java.io.IOException;

/**
 * Flash handles the display of temporary messages in the GUI.
 */
public class Flash {
    /**
     * Displays a temporary message in a popup window for the specified duration.
     *
     * @param gui         the MultiWindowTextGUI instance used to display the popup window
     * @param message     the message to display in the popup
     * @param milliseconds the duration in milliseconds for which the popup window should be visible
     * @throws IOException            if an input/output error occurs while displaying the popup
     * @throws InterruptedException   if the thread displaying the popup is interrupted
     */
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
