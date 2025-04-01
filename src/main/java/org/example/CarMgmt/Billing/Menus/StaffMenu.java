package org.example.CarMgmt.Billing.Menus;

import org.example.CarMgmt.Billing.MainMenu;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;

public class StaffMenu implements MenuStrategy {

	@Override
	public void populateMenuItems() {
		BasicWindow menuWindow = MainMenu.menuWindow;
		Panel panel = MainMenu.panel;
		Button generateInvoiceButton = new Button("Generate Invoice", () -> {
        	menuWindow.close();
            // TODO: show Login window
        });
		panel.addComponent(generateInvoiceButton);
	}
}
