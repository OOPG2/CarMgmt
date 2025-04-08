package org.example.CarMgmt.Billing.Invoices;

import org.example.CarMgmt.App;
import org.example.CarMgmt.Beans.Invoice;
import org.example.CarMgmt.Beans.Reservation;
import org.example.CarMgmt.Billing.MainMenu;
import org.example.CarMgmt.Billing.Payments.InvoiceRetriever;
import org.example.CarMgmt.Billing.Payments.ReservationRetriever;
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

public class InvoiceSearch {
	static public void showInvoiceSearchForm() {
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Search Invoice"));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
	    Button back = new Button("Back", () -> {
	    	menuWindow.close();
	    	MainMenu.showUserSelection();
	    });
	    panel.addComponent(back);
	    panel.addComponent(new Label("Invoice No."));
	    TextBox invoiceNoInput = new TextBox();
	    panel.addComponent(invoiceNoInput);
	    InvoiceRetriever invoiceRetriever = new InvoiceRetriever();
	    Button retrieveInvoice = new Button("Retrieve Invoice", () -> {
	    	try {
			Invoice invoice = invoiceRetriever.retrieveById(invoiceNoInput.getText());
			menuWindow.close();
			InvoiceStatusUpdater.showInvoiceStatusUpdater(invoice);
		} catch (RowNotFoundException e) {
			new MessageDialogBuilder()
	    		.setTitle("")
	    		.setText("Invoice cannot be found. Please check the invoice no.")
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
