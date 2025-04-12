package Reservations;



import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import objects.*;
/**
 * Screen for updating the status of a reservation (used by staff at pickup or return time).
 * Staff enters a reservation ID:
 * - If it's in "Reserved" status, it will be marked as "PickedUp".
 * - If it's in "PickedUp" status, it will be marked as "Returned".
 */
public class ReservationStatusUpdaterScreen extends BasicWindow {
    public ReservationStatusUpdaterScreen(ReservationManager manager) {
        super("Update Reservation Status");
        Panel panel = new Panel(new GridLayout(2));

        panel.addComponent(new Label("Reservation ID:"));
        TextBox resIdField = new TextBox();
        panel.addComponent(resIdField);

        Panel buttonPanel = new Panel(new GridLayout(2));
        Button updateButton = new Button("Update Status", () -> {
            try {
                int resId = Integer.parseInt(resIdField.getText().trim());
                Reservation res = manager.getReservationById(resId);
                if (res == null) {
                    throw new IllegalArgumentException("Reservation not found.");
                }
                String status = res.getStatus();
                if (status.equalsIgnoreCase("Reserved")) {
                    manager.confirmPickup(resId);
                    MessageDialog.showMessageDialog(getTextGUI(), "Success",
                            "Reservation " + resId + " marked as PickedUp.", MessageDialogButton.OK);
                } else if (status.equalsIgnoreCase("PickedUp")) {
                    manager.confirmReturn(resId);
                    MessageDialog.showMessageDialog(getTextGUI(), "Success",
                            "Reservation " + resId + " marked as Returned.", MessageDialogButton.OK);
                } else if (status.equalsIgnoreCase("Cancelled")) {
                    throw new IllegalArgumentException("Cannot update a cancelled reservation.");
                } else if (status.equalsIgnoreCase("Returned")) {
                    throw new IllegalArgumentException("Reservation is already returned.");
                } else {
                    throw new IllegalArgumentException("Status update not applicable for this reservation.");
                }
                this.close();
            } catch (NumberFormatException e) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error", "Reservation ID must be a number.", MessageDialogButton.OK);
            } catch (Exception e) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error", e.getMessage(), MessageDialogButton.OK);
            }
        });
        Button backButton = new Button("Back", this::close);
        buttonPanel.addComponent(updateButton);
        buttonPanel.addComponent(backButton);
        buttonPanel.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER, true, false, 2, 1));
        panel.addComponent(buttonPanel);

        setComponent(panel);
    }
}
