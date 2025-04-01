package org.example.CarMgmt.Billing.Menus;

import org.example.CarMgmt.Billing.MainMenu;
import org.example.CarMgmt.Billing.Payments.InvoiceSelector;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;

public class CustomerMenu implements MenuStrategy {

	@Override
	public void populateMenuItems() {
		BasicWindow menuWindow = MainMenu.menuWindow;
		Panel panel = MainMenu.panel;
		Button makePaymentsButton = new Button("Make Payments", () -> {
        	menuWindow.close();
        	new InvoiceSelector().showInvoiceSelector();
            // TODO: show Login window
        });
        Button paymentHistoryButton = new Button("Payment History", () -> {
        	menuWindow.close();
            // TODO: show CreateAccount window
        });
        Button viewMembershipPerksButton = new Button("View Membership Perks", () -> {
        	menuWindow.close();
            // TODO: show CreateAccount window
        });
        
        panel.addComponent(makePaymentsButton);
        panel.addComponent(paymentHistoryButton);
        panel.addComponent(viewMembershipPerksButton);
	}
}
