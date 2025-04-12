package menus;

import app.*;
import billing.invoice.InvoiceSearch;
import billing.invoice.InvoiceSelector;
import billing.invoice.ReservationSearch;
import billing.payment.PaymentHistoryListing;
import manager.*;
import objects.*;
import rewards.PerksViewer;
import Reservations.*;
import static menus.postLogin.Admin.CustomerManagementMenu.showCustomerManagementMenu;
import static menus.postLogin.Admin.StaffManagementMenu.showStaffManagementMenu;
import static menus.ProfileMenu.showProfileMenu;
import static menus.postLogin.Customer.CustomerVehicleManagementMenu.showCustomerVehicleManagementMenu;
import static menus.postLogin.VehicleManagementMenu.showVehicleManagementMenu;
import static menus.preLogin.MainMenu.showMainMenu;

import com.googlecode.lanterna.gui2.*;

public class LoggedMenu {
    public static void showLoggedMenu() {
        MultiWindowTextGUI gui = App.gui;

        AuthenticationManager authenticationManager = App.getAuthenticationManager();
        User loggedUser = authenticationManager.getLoggedUser();

        BasicWindow showLoggedWindow = new BasicWindow("OOP Rentals");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        panel.addComponent(new Label(String.format("Hello %s,\nwhat would you like to do?", loggedUser.getName())));

        panel.addComponent(new EmptySpace());

        if (loggedUser instanceof Customer) {
            Button vehiclesButton = new Button("Collect/Return Vehicles", () -> {
                showLoggedWindow.close();
                showCustomerVehicleManagementMenu(gui);
            });

            Button reservationsButton = new Button("Reservations (View/Modify)", () -> {
                showLoggedWindow.close();
                ReservationMenu.show(loggedUser, App.reservationManager);
            });


            Button rentalsButton = new Button("View Past Rentals", () -> {
                showLoggedWindow.close();
                showLoggedMenu();
            });
            Button invoicesButton = new Button("View & Pay Invoices", () -> {
                showLoggedWindow.close();
                InvoiceSelector.showInvoiceSelector();
            });

            Button paymentHistoryButton = new Button("View Payment History", () -> {
                showLoggedWindow.close();
                PaymentHistoryListing.showPaymentHistoryListing();
            });

            Button viewPerksButton = new Button("View Membership Perks", () -> {
                showLoggedWindow.close();
                PerksViewer.showPerks();
            });

            panel.addComponent(vehiclesButton);
            panel.addComponent(reservationsButton);
            panel.addComponent(rentalsButton);
            panel.addComponent(invoicesButton);
            panel.addComponent(paymentHistoryButton);
            panel.addComponent(viewPerksButton);
        }
        else if (loggedUser instanceof Staff) {
            Button vehiclesButton = new Button("Vehicle Management", () -> {
                showLoggedWindow.close();
                showVehicleManagementMenu(gui);
            });

            Button generateInvoiceButton = new Button("Generate Invoice", () -> {
                showLoggedWindow.close();
                ReservationSearch.showReservationSearchForm();
            });

            Button updateInvoiceStatus = new Button("Update Invoice Status", () -> {
                showLoggedWindow.close();
                InvoiceSearch.showInvoiceSearchForm();
            });


            panel.addComponent(vehiclesButton);
            panel.addComponent(generateInvoiceButton);
            panel.addComponent(updateInvoiceStatus);
        }
        else if (loggedUser instanceof Admin) {
            Button staffManagementButton = new Button("Staff Management", () -> {
                showLoggedWindow.close();
                showStaffManagementMenu(gui);
            });
            Button customerManagementButton = new Button("Customer Management", () -> {
                showLoggedWindow.close();
                showCustomerManagementMenu(gui);
            });
            Button vehiclesButton = new Button("Vehicle Management", () -> {
                showLoggedWindow.close();
                showVehicleManagementMenu(gui);
            });

            Button generateInvoiceButton = new Button("Generate Invoice", () -> {
                showLoggedWindow.close();
                ReservationSearch.showReservationSearchForm();
            });

            Button updateInvoiceStatus = new Button("Update Invoice Status", () -> {
                showLoggedWindow.close();
                InvoiceSearch.showInvoiceSearchForm();
            });

            panel.addComponent(staffManagementButton);
            panel.addComponent(customerManagementButton);
            panel.addComponent(vehiclesButton);
            panel.addComponent(generateInvoiceButton);
            panel.addComponent(updateInvoiceStatus);
        }

        Button profileButton = new Button("View Profile", () -> {
            showLoggedWindow.close();
            showProfileMenu(gui, null);
        });

        Button logoutButton = new Button("Logout", () -> {
            showLoggedWindow.close();
            authenticationManager.logoutUser();

            showMainMenu(gui);
        });

        panel.addComponent(profileButton);

        panel.addComponent(new EmptySpace());
        panel.addComponent(logoutButton);

        showLoggedWindow.setComponent(panel);
        gui.addWindowAndWait(showLoggedWindow);
    }
}
