package org.example.crms;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Screen for making a new reservation.
 * Allows the user to input a vehicle ID, start date-time, and end date-time, then creates the reservation if possible.
 */
public class MakeReservationScreen extends BasicWindow {
    public MakeReservationScreen(ReservationManager manager, User currentUser) {
        super("Make a Reservation");
        Panel panel = new Panel(new GridLayout(2));

        // Input fields for Vehicle ID and date-times
        panel.addComponent(new Label("Vehicle ID:"));
        TextBox vehicleField = new TextBox();
        panel.addComponent(vehicleField);

        panel.addComponent(new Label("Start (yyyy-MM-dd HH:mm):"));
        TextBox startField = new TextBox();
        panel.addComponent(startField);

        panel.addComponent(new Label("End (yyyy-MM-dd HH:mm):"));
        TextBox endField = new TextBox();
        panel.addComponent(endField);

        // Buttons for submit and cancel
        Panel buttonPanel = new Panel(new GridLayout(2));
        Button reserveButton = new Button("Reserve", () -> {
            try {
                int vehicleId = Integer.parseInt(vehicleField.getText().trim());
                LocalDateTime start = LocalDateTime.parse(startField.getText().trim(), Reservation.DATETIME_FORMAT);
                LocalDateTime end = LocalDateTime.parse(endField.getText().trim(), Reservation.DATETIME_FORMAT);
                manager.createReservation(currentUser, vehicleId, start, end);
                MessageDialog.showMessageDialog(getTextGUI(), "Success", "Reservation created successfully!", MessageDialogButton.OK);
                this.close();
            } catch (NumberFormatException e) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error", "Vehicle ID must be a number.", MessageDialogButton.OK);
            } catch (DateTimeParseException e) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error", "Invalid date/time format. Use yyyy-MM-dd HH:mm.", MessageDialogButton.OK);
            } catch (Exception e) {
                // Catch-all for other errors (like vehicle not available)
                MessageDialog.showMessageDialog(getTextGUI(), "Error", e.getMessage(), MessageDialogButton.OK);
            }
        });
        Button cancelButton = new Button("Cancel", this::close);
        buttonPanel.addComponent(reserveButton);
        buttonPanel.addComponent(cancelButton);
        // Span the button panel across 2 columns
        buttonPanel.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER, true, false, 2, 1));
        panel.addComponent(buttonPanel);

        setComponent(panel);
    }
}

