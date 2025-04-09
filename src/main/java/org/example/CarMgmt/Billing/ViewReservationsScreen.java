package org.example.CarMgmt.Billing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import org.example.CarMgmt.Beans.Reservation;
import org.example.CarMgmt.Billing.Payments.ReservationRetriever;
import org.example.CarMgmt.App;

/**
 * Screen to display current and past reservations for the logged-in customer.
 */
public class ViewReservationsScreen {
    public static void showReservations() {
        MultiWindowTextGUI gui = App.gui;
        BasicWindow menuWindow = new BasicWindow("Your Reservations");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        Button back = new Button("Back", () -> {
            menuWindow.close();
            MainMenu.showUserSelection();
        });
        panel.addComponent(back);

        // Panel to list reservations in a grid
        Panel listPanel = new Panel();
        listPanel.setLayoutManager(new GridLayout(5)); // 5 columns: ID, Vehicle, Start, End, Status
        GridLayout gridLayout = (GridLayout) listPanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        // Header row
        listPanel.addComponent(new Label("Res ID").addStyle(SGR.BOLD));
        listPanel.addComponent(new Label("Vehicle").addStyle(SGR.BOLD));
        listPanel.addComponent(new Label("Start Date").addStyle(SGR.BOLD));
        listPanel.addComponent(new Label("End Date").addStyle(SGR.BOLD));
        listPanel.addComponent(new Label("Status").addStyle(SGR.BOLD));

        // Load reservations and filter by current user
        new ReservationRetriever();
        HashMap<String, Reservation> allResMap = ReservationRetriever.reservations;
        List<Reservation> allReservations = new ArrayList<>(allResMap.values());
        String currentUserId = UserSelection.user.getUserId().toString();
        for (Reservation res : allReservations) {
            if (res.getUserId().equals(currentUserId)) {
                // List each reservation's details
                listPanel.addComponent(new Label(res.getId()));
                listPanel.addComponent(new Label(res.getVehicleId()));
                listPanel.addComponent(new Label(res.getStartDate()));
                listPanel.addComponent(new Label(res.getEndDate()));
                listPanel.addComponent(new Label(res.getStatus()));
            }
        }

        panel.addComponent(listPanel);
        menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
    }
}
