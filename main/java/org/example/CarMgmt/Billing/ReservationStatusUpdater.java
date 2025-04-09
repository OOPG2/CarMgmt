package org.example.CarMgmt.Billing;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;

import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import org.example.CarMgmt.Beans.Reservation;
import org.example.CarMgmt.Billing.Payments.ReservationRetriever;
import org.example.CarMgmt.App;

/**
 * Screen for staff to update a reservation's status (confirm pickup or mark return).
 */
public class ReservationStatusUpdater {
    public static void showReservationStatusUpdater() {
        MultiWindowTextGUI gui = App.gui;
        BasicWindow menuWindow = new BasicWindow("Update Reservation Status");
        Panel panel = new Panel(new GridLayout(1));

        Button back = new Button("Back", () -> {
            menuWindow.close();
            MainMenu.showUserSelection();
        });
        panel.addComponent(back);

        panel.addComponent(new Label("Reservation ID:").addStyle(SGR.BOLD));
        TextBox idInput = new TextBox();
        panel.addComponent(idInput);

        // Button to update status
        Button updateBtn = new Button("Update Status", () -> {
            String resId = idInput.getText().trim();
            ReservationRetriever retriever = new ReservationRetriever();
            Reservation res = null;
            try {
                res = retriever.retrieveById(resId);
            } catch (Exception e) {
                new MessageDialogBuilder()
                        .setTitle("Error")
                        .setText("Reservation not found.")
                        .build()
                        .showDialog(gui);
                return;
            }
            // Determine and perform the next status transition
            String currentStatus = res.getStatus();
            try {
                if (currentStatus.equalsIgnoreCase("reserved") || currentStatus.equalsIgnoreCase("confirmed")) {
                    // Confirm pickup
                    ReservationManager.confirmPickup(resId);
                    new MessageDialogBuilder()
                            .setTitle("Success")
                            .setText("Reservation marked as collected (pickup confirmed).")
                            .build()
                            .showDialog(gui);
                } else if (currentStatus.equalsIgnoreCase("collected")) {
                    // Confirm return
                    ReservationManager.confirmReturn(resId);
                    new MessageDialogBuilder()
                            .setTitle("Success")
                            .setText("Reservation marked as completed (vehicle returned).")
                            .build()
                            .showDialog(gui);
                } else if (currentStatus.equalsIgnoreCase("cancelled") || currentStatus.equalsIgnoreCase("completed")) {
                    new MessageDialogBuilder()
                            .setTitle("Notice")
                            .setText("Reservation is already " + currentStatus + ". No update needed.")
                            .build()
                            .showDialog(gui);
                } else {
                    // Fallback for any other status values
                    new MessageDialogBuilder()
                            .setTitle("Notice")
                            .setText("No status update available for current state: " + currentStatus)
                            .build()
                            .showDialog(gui);
                }
            } catch (Exception e) {
                new MessageDialogBuilder()
                        .setTitle("Error")
                        .setText("Status update failed:\n" + e.getMessage())
                        .build()
                        .showDialog(gui);
            }
            // After attempting update, close and return to staff menu
            menuWindow.close();
            MainMenu.showUserSelection();
        });
        panel.addComponent(new Label("")); // spacer
        panel.addComponent(updateBtn);

        menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
    }
}
