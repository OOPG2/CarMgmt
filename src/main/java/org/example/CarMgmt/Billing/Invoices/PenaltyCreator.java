package org.example.CarMgmt.Billing.Invoices;

import java.io.IOException;
import java.util.List;

import org.example.CarMgmt.App;
import org.example.CarMgmt.Beans.Penalty;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.table.Table;

public class PenaltyCreator {
	public void showPenaltyCreator(Table<String> penaltyTable) {
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Add Penalty"));
		menuWindow.setHints(java.util.Arrays.asList(Window.Hint.CENTERED));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
		panel.addComponent(new Label("Description"));
		TextBox descriptionInput = new TextBox(new TerminalSize(30, 1));
		panel.addComponent(descriptionInput);
		panel.addComponent(new Label("Amount"));
		TextBox amountInput = new TextBox();
		panel.addComponent(amountInput);
		Panel ctaPanel = new Panel();
		ctaPanel.setLayoutManager(new GridLayout(2));
		List<Penalty> penalties = InvoiceGenerator.penalties;
		Button addPenalty = new Button("Add Penalty", () -> {
			try {
				Penalty penalty = new Penalty();
				String description = descriptionInput.getText();
				String amount = amountInput.getText();
				Double parsedAmount = Double.parseDouble(amount);
				penalty.setDescription(description);
				penalty.setAmount(amount);
				penalties.add(penalty);
		    	penaltyTable.getTableModel().addRow(description, String.format("$%.2f", parsedAmount));
				menuWindow.close();
			} catch (NumberFormatException e) {
				
			}
			});
		ctaPanel.addComponent(addPenalty);
		Button cancel = new Button("Cancel", () -> {
			menuWindow.close();
		});
		ctaPanel.addComponent(cancel);
		panel.addComponent(ctaPanel);
		menuWindow.setComponent(panel);
	    gui.addWindowAndWait(menuWindow);
	}
}
