package org.example.CarMgmt.Billing.Payments;

import org.example.CarMgmt.App;
import org.example.CarMgmt.Helper.TotalRentalCalculator;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.table.Table;

public class InvoiceViewer {
	public static Table<String> table;
	public void showInvoice() {
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Invoice #%s", "1234"));
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));
        Button back = new Button("Back", () -> {
        	menuWindow.close();
        	new InvoiceSelector().showInvoiceSelector();
        });
        panel.addComponent(back);
        panel.addComponent(new Label("Pay via:"));
        Panel paymentMethodsPanel = new Panel();
        paymentMethodsPanel.setLayoutManager(new GridLayout(4));
        Button creditCard = new Button("Credit Card", () -> {
        	menuWindow.close();
        });
        Button payNow = new Button("PayNow", () -> {
        	menuWindow.close();
        });
        Button bankTransfer = new Button("Bank Transfer", () -> {
        	menuWindow.close();
        });
        paymentMethodsPanel.addComponent(creditCard);
        paymentMethodsPanel.addComponent(payNow);
        paymentMethodsPanel.addComponent(bankTransfer);
        panel.addComponent(paymentMethodsPanel);
    	table = new Table<String>("Description", "Amount");
    	Double totalRental = new TotalRentalCalculator().calculateRental(null);
    	table.getTableModel().addRow("Vehicle Rental", String.format("$%.2f", totalRental));
    	try {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	panel.addComponent(table);
    	menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
	}
}
