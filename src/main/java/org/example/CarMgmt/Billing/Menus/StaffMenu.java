package org.example.CarMgmt.Billing.Menus;

import org.example.CarMgmt.Billing.MainMenu;
import org.example.CarMgmt.Billing.Invoices.ReservationSearch;

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
            new ReservationSearch().showReservationSearchForm();
        });
		panel.addComponent(generateInvoiceButton);
	}
}
