package billing.invoice;

import java.util.List;

import app.*;
import constants.*;
import beans.*;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.table.Table;

public class PenaltyCreator {
	public void showPenaltyCreator(Table<String> penaltyTable, List<Penalty> penalties) {
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Add Penalty"));
		menuWindow.setHints(java.util.Arrays.asList(Window.Hint.CENTERED));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
		panel.addComponent(new Label("Description").addStyle(SGR.BOLD));
		//TextBox descriptionInput = new TextBox(new TerminalSize(30, 1));
		//panel.addComponent(descriptionInput);
		ComboBox<String> comboBox = new ComboBox<String>();
		PredefinedPenalties predefinedPenalties = new PredefinedPenalties();
		for (String p: predefinedPenalties.getPredefinedPenalties().keySet()) {
			comboBox.addItem(p);
		}
		String initPenalty = comboBox.getText();
		panel.addComponent(comboBox);
		panel.addComponent(new EmptySpace());
		panel.addComponent(new Label("Amount").addStyle(SGR.BOLD));
		Label amountLabel = new Label(String.format("$%8.2f", PredefinedPenalties.penalties.get(initPenalty)));
		comboBox.addListener((selectedIndex, previousSelection, changedByUserInteraction) -> {
			if (selectedIndex != previousSelection) {
				String selected = comboBox.getItem(selectedIndex);
				amountLabel.setText(String.format("$%8.2f", PredefinedPenalties.penalties.get(selected)));
			}
		});
		panel.addComponent(amountLabel);
		panel.addComponent(new EmptySpace());
		Panel ctaPanel = new Panel();
		ctaPanel.setLayoutManager(new GridLayout(2));
		Button addPenalty = new Button("Add Penalty", () -> {
			try {
				Penalty penalty = new Penalty();
				String selectedPenalty = comboBox.getText();
				Double amount = PredefinedPenalties.penalties.get(selectedPenalty);
				penalty.setDescription(selectedPenalty);
				penalty.setAmount(amount.toString());
				penalties.add(penalty);
		    	penaltyTable.getTableModel().addRow(selectedPenalty, String.format("$%8.2f", amount));
				menuWindow.close();
			} catch (NumberFormatException e) {
				e.printStackTrace();
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
