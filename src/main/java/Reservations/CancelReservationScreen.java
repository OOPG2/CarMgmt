package Reservations;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import java.util.List;
import objects.*;

/**
 * Screen for canceling an existing reservation.
 * Allows selection of one of the user's current reservations (or any reservation if staff) to cancel.
 */
public class CancelReservationScreen extends BasicWindow {
    public CancelReservationScreen(ReservationManager manager, User currentUser) {
        super("Cancel Reservation");
        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));

        // Determine which reservations to list (active reservations only)
        List<Reservation> cancelable;
        if (currentUser.getRole().equalsIgnoreCase("Staff")) {
            // Staff can cancel any reservation that is still "Reserved"
            cancelable = manager.getAllReservations();
        } else {
            // Customer can cancel their own reservations
            cancelable = manager.getReservationsForUser(currentUser.getUserId());
        }
        // Filter out reservations that are not in "Reserved" status
        cancelable.removeIf(r -> !r.getStatus().equalsIgnoreCase("Reserved"));

        if (cancelable.isEmpty()) {
            panel.addComponent(new Label("No active reservations available to cancel."));
        } else {
            panel.addComponent(new Label("Select a reservation to cancel:"));
            ActionListBox listBox = new ActionListBox();
            for (Reservation res : cancelable) {
                Vehicle vehicle = manager.getVehicleById(res.getVehicleId());
                User resUser = manager.getUserById(res.getUserId());
                String vehicleInfo = (vehicle != null)
                        ? vehicle.getMake() + " " + vehicle.getModel()
                        : ("Vehicle " + res.getVehicleId());
                String userInfo = (resUser != null) ? resUser.getUserId() : String.valueOf(res.getUserId());
                String itemLabel;
                if (currentUser.getRole().equalsIgnoreCase("Staff")) {
                    // Staff sees which customer made the reservation
                    itemLabel = "ID " + res.getId() + " - [" + userInfo + "] " + vehicleInfo +
                            " (" + res.getStart() + " to " + res.getEnd() + ")";
                } else {
                    itemLabel = "ID " + res.getId() + " - " + vehicleInfo +
                            " (" + res.getStart() + " to " + res.getEnd() + ")";
                }
                listBox.addItem(itemLabel, () -> {
                    try {
                        manager.cancelReservation(currentUser, res.getId());
                        MessageDialog.showMessageDialog(getTextGUI(), "Success",
                                "Reservation " + res.getId() + " has been cancelled.", MessageDialogButton.OK);
                        this.close();
                    } catch (Exception e) {
                        MessageDialog.showMessageDialog(getTextGUI(), "Error", e.getMessage(), MessageDialogButton.OK);
                    }
                });
            }
            panel.addComponent(listBox);
        }

        panel.addComponent(new Button("Back", this::close));
        setComponent(panel);
    }
}
