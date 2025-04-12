package Reservations;


import beans.Reservation;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import objects.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
/**
 * Screen for modifying an existing reservation's details (vehicle or time).
 * Only reservations that have not been picked up (status "Reserved") can be modified.
 * Customers can only modify their own reservations.
 */
public class ModifyReservationScreen extends BasicWindow {
    public ModifyReservationScreen(ReservationManager manager, User currentUser) {
        super("Modify Reservation");
        Panel panel = new Panel(new GridLayout(2));

        panel.addComponent(new Label("Reservation ID:"));
        TextBox resIdField = new TextBox();
        panel.addComponent(resIdField);

        panel.addComponent(new Label("New Vehicle ID (optional):"));
        TextBox vehicleField = new TextBox();
        panel.addComponent(vehicleField);

        panel.addComponent(new Label("New Start (yyyy-MM-dd):"));
        TextBox startField = new TextBox();
        panel.addComponent(startField);

        panel.addComponent(new Label("New End (yyyy-MM-dd):"));
        TextBox endField = new TextBox();
        panel.addComponent(endField);

        Panel buttonPanel = new Panel(new GridLayout(2));
        Button saveButton = new Button("Save Changes", () -> {
            try {
                int resId = Integer.parseInt(resIdField.getText().trim());
                Integer newVehicleId = null;
                String vehicleText = vehicleField.getText().trim();
                if (!vehicleText.isEmpty()) {
                    newVehicleId = Integer.parseInt(vehicleText);
                }
                LocalDate newStartDate = LocalDate.parse(startField.getText().trim(), Reservation.DATETIME_FORMAT);
                LocalDate newEndDate = LocalDate.parse(endField.getText().trim(), Reservation.DATETIME_FORMAT);
                LocalDateTime newStart = newStartDate.atStartOfDay();
                LocalDateTime newEnd = newEndDate.atStartOfDay();

                manager.modifyReservation(currentUser, resId, newVehicleId, newStart, newEnd);
                MessageDialog.showMessageDialog(getTextGUI(), "Success", "Reservation modified successfully!", MessageDialogButton.OK);
                this.close();
            } catch (NumberFormatException e) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error", "Please enter numeric values for IDs.", MessageDialogButton.OK);
            } catch (DateTimeParseException e) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error", "Invalid date/time format. Use yyyy-MM-dd.", MessageDialogButton.OK);
            } catch (Exception e) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error", e.getMessage(), MessageDialogButton.OK);
            }
        });
        Button cancelButton = new Button("Cancel", this::close);
        buttonPanel.addComponent(saveButton);
        buttonPanel.addComponent(cancelButton);
        buttonPanel.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER, true, false, 2, 1));
        panel.addComponent(buttonPanel);

        setComponent(panel);
    }
}
