package org.example.CarMgmt.Billing.Menus;

import org.example.CarMgmt.Billing.MainMenu;
import org.example.CarMgmt.Billing.MakeReservationScreen;
import org.example.CarMgmt.Billing.ViewReservationsScreen;
import org.example.CarMgmt.Billing.ModifyReservationScreen;
import org.example.CarMgmt.Billing.CancelReservationScreen;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;

public class CustomerMenu implements MenuStrategy {
	@Override
	public void populateMenuItems() {
		BasicWindow menuWindow = MainMenu.menuWindow;
		Panel panel = MainMenu.panel;

		Button makeReservationButton = new Button("Make Reservation", () -> {
			menuWindow.close();
			MakeReservationScreen.showMakeReservation();
		});
		Button viewReservationsButton = new Button("View Reservations", () -> {
			menuWindow.close();
			ViewReservationsScreen.showReservations();
		});
		Button modifyReservationButton = new Button("Modify Reservation", () -> {
			menuWindow.close();
			ModifyReservationScreen.showModifyReservation();
		});
		Button cancelReservationButton = new Button("Cancel Reservation", () -> {
			menuWindow.close();
			CancelReservationScreen.showCancelReservation();
		});
		Button makePaymentsButton = new Button("View Invoice & Make Payment", () -> {
			menuWindow.close();
			new org.example.CarMgmt.Billing.Payments.InvoiceSelector().showInvoiceSelector();
		});
		Button paymentHistoryButton = new Button("Payment History", () -> {
			menuWindow.close();
			org.example.CarMgmt.Billing.PaymentHistoryListing.showPaymentHistoryListing();
		});
		Button viewMembershipPerksButton = new Button("View Membership Perks", () -> {
			menuWindow.close();
			new org.example.CarMgmt.Rewards.PerksViewer().showPerks();
		});

		panel.addComponent(makeReservationButton);
		panel.addComponent(viewReservationsButton);
		panel.addComponent(modifyReservationButton);
		panel.addComponent(cancelReservationButton);
		panel.addComponent(makePaymentsButton);
		panel.addComponent(paymentHistoryButton);
		panel.addComponent(viewMembershipPerksButton);
	}
}
