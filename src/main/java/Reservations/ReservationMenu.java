package Reservations;

import app.App;
import com.googlecode.lanterna.gui2.*;
import objects.User;

public class ReservationMenu extends BasicWindow {
    public ReservationMenu(ReservationManager manager, User user) {
        super("Reservation Menu");

        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));

        panel.addComponent(new Label("Hello, " + user.getUserId() + ". Choose an option:"));

        if (user.getRole().equalsIgnoreCase("Customer")) {
            panel.addComponent(new Button("Make a Reservation", () -> {
                MakeReservationScreen makeScreen = new MakeReservationScreen(manager, user);
                App.gui.addWindowAndWait(makeScreen);
            }));

            panel.addComponent(new Button("View My Reservations", () -> {
                ViewReservationsScreen viewScreen = new ViewReservationsScreen(manager, user);
                App.gui.addWindowAndWait(viewScreen);
            }));

            panel.addComponent(new Button("Modify a Reservation", () -> {
                ModifyReservationScreen modScreen = new ModifyReservationScreen(manager, user);
                App.gui.addWindowAndWait(modScreen);
            }));

            panel.addComponent(new Button("Cancel a Reservation", () -> {
                CancelReservationScreen cancelScreen = new CancelReservationScreen(manager, user);
                App.gui.addWindowAndWait(cancelScreen);
            }));
        }

        if (user.getRole().equalsIgnoreCase("Staff")) {
            panel.addComponent(new Button("View All Reservations", () -> {
                ViewReservationsScreen viewScreen = new ViewReservationsScreen(manager, user);
                App.gui.addWindowAndWait(viewScreen);
            }));

            panel.addComponent(new Button("Update Reservation Status", () -> {
                ReservationStatusUpdaterScreen statusScreen = new ReservationStatusUpdaterScreen(manager);
                App.gui.addWindowAndWait(statusScreen);
            }));
        }

        panel.addComponent(new Button("Back to Main Menu", () -> {
            this.close();  // Close the current ReservationMenu
            menus.LoggedMenu.showLoggedMenu();  // Reopen the LoggedMenu
        }));

        setComponent(panel);
    }

    public static void show(User user, ReservationManager manager) {
        ReservationMenu menu = new ReservationMenu(manager, user);
        App.gui.addWindowAndWait(menu);
    }
}