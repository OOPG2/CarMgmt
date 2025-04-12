package billing.invoice;

import app.App;
import beans.Invoice;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import exceptions.RowNotFoundException;
import menus.LoggedMenu;

public class InvoiceSearch {
	public static void showInvoiceSearchForm() {
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Search Invoice"));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
	    Button back = new Button("Back", () -> {
	    	menuWindow.close();
	    	LoggedMenu.showLoggedMenu();
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
