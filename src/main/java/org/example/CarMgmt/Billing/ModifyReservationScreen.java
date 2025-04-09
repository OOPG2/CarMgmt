package org.example.CarMgmt.Billing;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
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
 * Screen for customers to modify an existing reservation (if not yet collected).
 */
public class ModifyReservationScreen {
    public static void showModifyReservation() {
        MultiWindowTextGUI gui = App.gui;
        BasicWindow menuWindow = new BasicWindow("Modify Reservation");
        Panel mainPanel = new Panel(new GridLayout(1));

        Button back = new Button("Back", () -> {
            menuWindow.close();
            MainMenu.showUserSelection();
        });
        mainPanel.addComponent(back);

        // Input panel for Reservation ID
        Panel idPanel = new Panel(new GridLayout(2));
        idPanel.addComponent(new Label("Reservation ID:").addStyle(SGR.BOLD));
        TextBox idInput = new TextBox(new TerminalSize(15, 1));
        idPanel.addComponent(idInput);
        mainPanel.addComponent(idPanel);

        // Panel for new details (initially hidden until reservation is fetched)
        Panel formPanel = new Panel(new GridLayout(2));
        Label startLabel = new Label("New Start Date (YYYY-MM-DD):").addStyle(SGR.BOLD);
        TextBox newStartInput = new TextBox(new TerminalSize(15, 1));
        Label endLabel = new Label("New End Date (YYYY-MM-DD):").addStyle(SGR.BOLD);
        TextBox newEndInput = new TextBox(new TerminalSize(15, 1));
        Label insuranceLabel = new Label("New Insurance (Amount):").addStyle(SGR.BOLD);
        TextBox newInsuranceInput = new TextBox(new TerminalSize(15, 1));
        Label notesLabel = new Label("New Notes:").addStyle(SGR.BOLD);
        TextBox newNotesInput = new TextBox(new TerminalSize(15, 1));
        // Add components to formPanel (they will be visible after retrieving reservation)
        formPanel.addComponent(startLabel);
        formPanel.addComponent(newStartInput);
        formPanel.addComponent(endLabel);
        formPanel.addComponent(newEndInput);
        formPanel.addComponent(insuranceLabel);
        formPanel.addComponent(newInsuranceInput);
        formPanel.addComponent(notesLabel);
        formPanel.addComponent(newNotesInput);
        formPanel.setVisible(false); // hide initially
        mainPanel.addComponent(formPanel);

        mainPanel.addComponent(new EmptySpace());

        // Button to fetch reservation details
        Button fetchButton = new Button("Load Reservation", () -> {
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
            // Ensure reservation can be modified
            if (res.getStatus().equalsIgnoreCase("collected") || res.getStatus().equalsIgnoreCase("completed")) {
                new MessageDialogBuilder()
                        .setTitle("Unavailable")
                        .setText("This reservation cannot be modified (already collected or completed).")
                        .build()
                        .showDialog(gui);
                return;
            }
            // Populate current values in the input fields for reference (optional)
            newStartInput.setText(res.getStartDate());
            newEndInput.setText(res.getEndDate());
            newInsuranceInput.setText(res.getInsurance());
            newNotesInput.setText(res.getCustomerNotes());
            formPanel.setVisible(true);
        });
        mainPanel.addComponent(fetchButton);

        // Modify button to submit changes
        Button modifyButton = new Button("Save Changes", () -> {
            String resId = idInput.getText().trim();
            String newStart = newStartInput.getText().trim();
            String newEnd = newEndInput.getText().trim();
            String newInsurance = newInsuranceInput.getText().trim();
            String newNotes = newNotesInput.getText().trim();
            try {
                ReservationManager.modifyReservation(resId, newStart, newEnd,
                        newInsurance.isEmpty()? null : newInsurance,
                        newNotes.isEmpty()? null : newNotes);
                menuWindow.close();
                new MessageDialogBuilder()
                        .setTitle("Success")
                        .setText("Reservation modified successfully.")
                        .build()
                        .showDialog(gui);
                MainMenu.showUserSelection();
            } catch (Exception e) {
                new MessageDialogBuilder()
                        .setTitle("Error")
                        .setText("Modification failed:\n" + e.getMessage())
                        .build()
                        .showDialog(gui);
            }
        });
        mainPanel.addComponent(modifyButton);

        menuWindow.setComponent(mainPanel);
        gui.addWindowAndWait(menuWindow);
    }
}
