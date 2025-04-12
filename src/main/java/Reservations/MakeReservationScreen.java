package Reservations;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import objects.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class MakeReservationScreen extends BasicWindow {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MakeReservationScreen(ReservationManager manager, User currentUser) {
        super("Make a Reservation");

        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));

        // Fetch available vehicles
        List<Vehicle> availableVehicles = manager.getAllVehicles().stream()
                .filter(v -> "Available".equalsIgnoreCase(v.getStatus()))
                .collect(Collectors.toList());

        if (availableVehicles.isEmpty()) {
            panel.addComponent(new Label("No vehicles currently available for reservation."));
            panel.addComponent(new Button("Back", this::close));
            setComponent(panel);
            return;
        }


        panel.addComponent(new Label("Select a vehicle to reserve:"));

        ActionListBox vehicleList = new ActionListBox(new TerminalSize(60, 10));
        panel.addComponent(vehicleList);

        TextBox startField = new TextBox().setPreferredSize(new TerminalSize(20, 1));
        TextBox endField = new TextBox().setPreferredSize(new TerminalSize(20, 1));

        for (Vehicle vehicle : availableVehicles) {
            String label = String.format("%s %s (%s) - $%.2f/day",
                    vehicle.getMake(), vehicle.getModel(), vehicle.getPlate(), vehicle.getRentalCost());

            vehicleList.addItem(label, () -> {
                Panel confirmPanel = new Panel(new GridLayout(2));
                confirmPanel.addComponent(new Label("Start Date (yyyy-MM-dd):"));
                confirmPanel.addComponent(startField);
                confirmPanel.addComponent(new Label("End Date (yyyy-MM-dd):"));
                confirmPanel.addComponent(endField);

                Button confirmButton = new Button("Confirm Reservation", () -> {
                    try {
                        LocalDate startDate = LocalDate.parse(startField.getText().trim(), DATE_FORMAT);
                        LocalDate endDate = LocalDate.parse(endField.getText().trim(), DATE_FORMAT);
                        LocalDateTime start = startDate.atStartOfDay();
                        LocalDateTime end = endDate.atStartOfDay();

                        Reservation reservation = manager.createReservation(currentUser, vehicle.getId(), start, end);
                        vehicle.setStatus("Not Available");
                        manager.saveVehicles();

                        String confirmation = String.format(
                                "Reservation Confirmed!\n\nReservation ID: %d\nVehicle: %s %s (%s)\nCost per Day: $%.2f\nRental Dates: %s to %s",
                                reservation.getId(),
                                vehicle.getMake(),
                                vehicle.getModel(),
                                vehicle.getPlate(),
                                vehicle.getRentalCost(),
                                start.toLocalDate(),
                                end.toLocalDate()
                        );

                        MessageDialog.showMessageDialog(getTextGUI(), "Success", confirmation, MessageDialogButton.OK);
                        this.close();
                    } catch (DateTimeParseException e) {
                        MessageDialog.showMessageDialog(getTextGUI(), "Error", "Invalid date format. Use yyyy-MM-dd.", MessageDialogButton.OK);
                    } catch (Exception e) {
                        MessageDialog.showMessageDialog(getTextGUI(), "Error", e.getMessage(), MessageDialogButton.OK);
                    }
                });

                confirmPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
                confirmPanel.addComponent(confirmButton);
                this.setComponent(confirmPanel);
            });
        }

        panel.addComponent(new EmptySpace());
        panel.addComponent(new Button("Back", this::close));

        setComponent(panel);
    }
}
