package org.example.CarMgmt.Billing.Invoices;

import org.example.CarMgmt.App;
import org.example.CarMgmt.Beans.Reservation;
import org.example.CarMgmt.Billing.MainMenu;
import org.example.CarMgmt.Billing.Payments.PenaltyWriter;
import org.example.CarMgmt.Exceptions.RowNotFoundException;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

public class ReservationSearch {
	public void showReservationSearchForm() {
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Search Reservation"));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
	    Button back = new Button("Back", () -> {
	    	menuWindow.close();
	    	MainMenu.showUserSelection();
	    });
	    panel.addComponent(back);
	    panel.addComponent(new Label("Reservation No."));
	    TextBox reservationNoInput = new TextBox();
	    panel.addComponent(reservationNoInput);
	    PenaltyWriter.ReservationRetriever reservationRetriever = new PenaltyWriter.ReservationRetriever();
	    Button retrieveInvoice = new Button("Retrieve Reservation", () -> {
	    	try {
			Reservation reservation = reservationRetriever.retrieveById(reservationNoInput.getText());
			menuWindow.close();
			new InvoiceGenerator().showInvoiceGenerator(reservation);
		} catch (RowNotFoundException e) {
			new MessageDialogBuilder()
	    		.setTitle("")
	    		.setText("Reservation cannot be found. Please check the reservation no.")
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
