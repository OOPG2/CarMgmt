package billing.invoice;

import Reservations.ReservationManager;
import app.App;
import beans.Reservation;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import exceptions.RowNotFoundException;
import menus.LoggedMenu;

public class ReservationSearch {
	public static void showReservationSearchForm() {
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Search Reservation"));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
		Button back = new Button("Back", () -> {
			menuWindow.close();
			LoggedMenu.showLoggedMenu();
		});
		panel.addComponent(back);
		panel.addComponent(new Label("Reservation No."));
		TextBox reservationNoInput = new TextBox();
		panel.addComponent(reservationNoInput);
		//ReservationRetriever reservationRetriever = new ReservationRetriever();
		ReservationManager reservationManager = App.reservationManager;
		Button retrieveInvoice = new Button("Retrieve Reservation", () -> {
			try {
				Reservation reservation = reservationManager.getReservationById(Integer.parseInt(reservationNoInput.getText())); //reservationRetriever.retrieveById(reservationNoInput.getText());
				if (reservation == null) {throw new RowNotFoundException("");}
				menuWindow.close();
				new InvoiceGenerator().showInvoiceGenerator(reservation);
			} catch (RowNotFoundException e) {
				new MessageDialogBuilder()
						.setTitle("")
						.setText("Reservation cannot be found. Please check the reservation no.")
						.build()
						.showDialog(gui);
			} catch (NumberFormatException e) {
				new MessageDialogBuilder()
						.setTitle("")
						.setText("Reservation no. must be an integer. Please check the reservation no.")
						.build()
						.showDialog(gui);
			}
		});
		panel.addComponent(new EmptySpace());
		panel.addComponent(retrieveInvoice);
		menuWindow.setComponent(panel);
		gui.addWindowAndWait(menuWindow);
	}
}
