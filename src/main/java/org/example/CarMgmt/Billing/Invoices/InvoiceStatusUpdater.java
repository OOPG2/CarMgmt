package org.example.CarMgmt.Billing.Invoices;

import java.io.IOException;
import java.util.List;

import org.example.CarMgmt.App;
import org.example.CarMgmt.InvoiceStatuses;
import org.example.CarMgmt.PredefinedPenalties;
import org.example.CarMgmt.Beans.Invoice;
import org.example.CarMgmt.Beans.Penalty;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Interactable.FocusChangeDirection;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.table.Table;

public class InvoiceStatusUpdater {
	static public void showInvoiceStatusUpdater(Invoice invoice) {
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Update Invoice Status"));
		menuWindow.setHints(java.util.Arrays.asList(Window.Hint.CENTERED));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
		panel.addComponent(new Label("Status").addStyle(SGR.BOLD));
		//TextBox descriptionInput = new TextBox(new TerminalSize(30, 1));
		//panel.addComponent(descriptionInput);
		ComboBox<String> comboBox = new ComboBox<String>();
		List<String> invoiceStatuses = new InvoiceStatuses().getInvoiceStatuses();
		for (String s: invoiceStatuses) {
			comboBox.addItem(s);
		}
		comboBox.setSelectedItem(invoice.getStatus());
		panel.addComponent(comboBox);
		panel.addComponent(new EmptySpace());
		Panel ctaPanel = new Panel();
		ctaPanel.setLayoutManager(new GridLayout(2));
		Button editStatus = new Button("Edit Status", () -> {
			try {
				String selectedStatus = comboBox.getText();
				invoice.setStatus(selectedStatus);
				menuWindow.close();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			});
		ctaPanel.addComponent(editStatus);
		Button cancel = new Button("Cancel", () -> {
			menuWindow.close();
		});
		ctaPanel.addComponent(cancel);
		panel.addComponent(ctaPanel);
		menuWindow.setComponent(panel);
	    gui.addWindowAndWait(menuWindow);
	}
}
