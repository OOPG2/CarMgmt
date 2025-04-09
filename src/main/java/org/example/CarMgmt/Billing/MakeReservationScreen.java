package org.example.CarMgmt.Billing;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import org.example.CarMgmt.App;

/**
 * Screen for customers to make a new reservation.
 */
public class MakeReservationScreen {
    public static void showMakeReservation() {
        MultiWindowTextGUI gui = App.gui;
        BasicWindow menuWindow = new BasicWindow("Make a Reservation");
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new GridLayout(1));

        // Back button to return to main menu
        Button back = new Button("Back", () -> {
            menuWindow.close();
            MainMenu.showUserSelection();
        });
        mainPanel.addComponent(back);

        // Form panel with two columns (label and input)
        Panel formPanel = new Panel();
        formPanel.setLayoutManager(new GridLayout(2));
        formPanel.addComponent(new Label("Vehicle ID:").addStyle(SGR.BOLD));
        TextBox vehicleIdInput = new TextBox(new TerminalSize(20, 1));
        formPanel.addComponent(vehicleIdInput);
        formPanel.addComponent(new Label("Start Date (YYYY-MM-DD):").addStyle(SGR.BOLD));
        TextBox startDateInput = new TextBox(new TerminalSize(20, 1));
        formPanel.addComponent(startDateInput);
        formPanel.addComponent(new Label("End Date (YYYY-MM-DD):").addStyle(SGR.BOLD));
        TextBox endDateInput = new TextBox(new TerminalSize(20, 1));
        formPanel.addComponent(endDateInput);
        formPanel.addComponent(new Label("Daily Rental Rate:").addStyle(SGR.BOLD));
        TextBox dailyRateInput = new TextBox(new TerminalSize(20, 1));
        formPanel.addComponent(dailyRateInput);
        formPanel.addComponent(new Label("Insurance (Amount):").addStyle(SGR.BOLD));
        TextBox insuranceInput = new TextBox(new TerminalSize(20, 1));
        formPanel.addComponent(insuranceInput);
        formPanel.addComponent(new Label("Customer Notes:").addStyle(SGR.BOLD));
        TextBox notesInput = new TextBox(new TerminalSize(20, 1));
        formPanel.addComponent(notesInput);

        mainPanel.addComponent(formPanel);
        mainPanel.addComponent(new EmptySpace()); // spacer

        // Submit button panel
        Panel buttonPanel = new Panel(new GridLayout(1));
        Button submit = new Button("Submit", () -> {
            String vehicleId = vehicleIdInput.getText().trim();
            String startDate = startDateInput.getText().trim();
            String endDate = endDateInput.getText().trim();
            String dailyRate = dailyRateInput.getText().trim();
            String insurance = insuranceInput.getText().trim();
            String notes = notesInput.getText().trim();
            try {
                // Use current user from UserSelection for userId
                String userId = UserSelection.user.getUserId().toString();
                ReservationManager.createReservation(userId, vehicleId, startDate, endDate, dailyRate, insurance, notes);
                menuWindow.close();
                // Show success message
                new MessageDialogBuilder()
                        .setTitle("Success")
                        .setText("Reservation created successfully.")
                        .build()
                        .showDialog(gui);
                // After success, return to main menu
                MainMenu.showUserSelection();
            } catch (Exception e) {
                // Show error dialog if reservation could not be created (e.g., unavailable or invalid input)
                new MessageDialogBuilder()
                        .setTitle("Error")
                        .setText("Failed to create reservation:\n" + e.getMessage())
                        .build()
                        .showDialog(gui);
            }
        });
        buttonPanel.addComponent(submit);
        mainPanel.addComponent(buttonPanel);

        menuWindow.setComponent(mainPanel);
        gui.addWindowAndWait(menuWindow);
    }
}
