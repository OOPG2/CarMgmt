package org.example.CarMgmt.Billing.Payments;

import org.example.CarMgmt.App;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.table.Table;

public class InvoiceSelector {
	public static Table<String> table;
	public void showInvoiceSelector() {
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow("Select invoice to pay");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));
    	table = new Table<String>("Invoice No.", "Reservation No.", "Total Amount", "Due");
    	try {
			new InvoicePopulator().populateInvoices();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	panel.addComponent(table);
    	menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
	}
}
