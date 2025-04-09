package org.example.CarMgmt.Billing;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;

import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import org.example.CarMgmt.App;

/**
 * Screen for customers to cancel an existing reservation (before pickup).
 */
public class CancelReservationScreen {
    public static void showCancelReservation() {
        MultiWindowTextGUI gui = App.gui;
        BasicWindow menuWindow = new BasicWindow("Cancel Reservation");
        Panel panel = new Panel(new GridLayout(1));

        Button back = new Button("Back", () -> {
            menuWindow.close();
            MainMenu.showUserSelection();
        });
        panel.addComponent(back);

        // Input for reservation ID
        Panel inputPanel = new Panel(new GridLayout(2));
        inputPanel.addComponent(new Label("Reservation ID:").addStyle(SGR.BOLD));
        TextBox idInput = new TextBox(new TerminalSize(15, 1));
        inputPanel.addComponent(idInput);
        panel.addComponent(inputPanel);

        // Cancel button
        Button cancelBtn = new Button("Cancel Reservation", () -> {
            String resId = idInput.getText().trim();
            try {
                // Attempt cancellation
                ReservationManager.cancelReservation(resId);
                menuWindow.close();
                new MessageDialogBuilder()
                        .setTitle("Cancelled")
                        .setText("Reservation " + resId + " has been cancelled.")
                        .build()
                        .showDialog(gui);
                MainMenu.showUserSelection();
            } catch (Exception e) {
                new MessageDialogBuilder()
                        .setTitle("Error")
                        .setText("Cancellation failed:\n" + e.getMessage())
                        .build()
                        .showDialog(gui);
            }
        });
        panel.addComponent(cancelBtn);

        menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
    }
}
