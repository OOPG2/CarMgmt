package Reservations;

import beans.Reservation;
import beans.Vehicle;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import objects.User;

import java.util.List;
/**
 * Screen for viewing reservations.
 * - Customers see only their own reservations.
 * - Staff see all reservations in the system.
 */
public class ViewReservationsScreen extends BasicWindow {
    public ViewReservationsScreen(ReservationManager manager, User currentUser) {
        super("View Reservations");
        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));

        List<Reservation> list;
        if (currentUser.getRole().equalsIgnoreCase("Staff")) {
            // Staff views all reservations
            list = manager.getAllReservations();
        } else {
            // Customer views only their reservations
            list = manager.getReservationsForUser(currentUser.getUserId());
        }

        if (list.isEmpty()) {
            panel.addComponent(new Label("No reservations found."));
        } else {
            for (Reservation res : list) {
                Vehicle vehicle = manager.getVehicleById(res.getVehicleId());
                User user = manager.getUserById(res.getUserId());
                String vehicleInfo = (vehicle != null)
                        ? (vehicle.getMake() + " " + vehicle.getModel())
                        : ("Vehicle " + res.getVehicleId());
                String userInfo = (user != null) ? user.getUserId() : String.valueOf(res.getUserId());
                String line;
                if (currentUser.getRole().equalsIgnoreCase("Staff")) {
                    // Staff sees user info as well
                    line = "Reservation " + res.getId() + " | User: " + userInfo +
                            " | Vehicle: " + vehicleInfo +
                            " | " + res.getStart() + " to " + res.getEnd() +
                            " | Status: " + res.getStatus();
                } else {
                    // Customer view (no need to show username)
                    line = "Reservation " + res.getId() + " | Vehicle: " + vehicleInfo +
                            " | " + res.getStart() + " to " + res.getEnd() +
                            " | Status: " + res.getStatus();
                }
                panel.addComponent(new Label(line));
            }
        }

        // Add a blank line and a back button
        panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        panel.addComponent(new Button("Back", this::close));

        setComponent(panel);
    }
}

