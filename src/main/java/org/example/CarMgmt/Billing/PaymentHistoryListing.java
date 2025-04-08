package org.example.CarMgmt.Billing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.example.CarMgmt.App;
import org.example.CarMgmt.Beans.PaymentHistory;
import org.example.CarMgmt.Beans.Reservation;
import org.example.CarMgmt.Billing.Invoices.InvoiceGenerator;
import org.example.CarMgmt.Billing.Payments.PaymentHistoryRetriever;
import org.example.CarMgmt.Billing.Payments.ReservationRetriever;
import org.example.CarMgmt.Exceptions.RowNotFoundException;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

public class PaymentHistoryListing {
	static public void showPaymentHistoryListing() {
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Payment Histories"));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
	    Button back = new Button("Back", () -> {
	    	menuWindow.close();
	    	MainMenu.showUserSelection();
	    });
	    panel.addComponent(back);
	    Panel historyListingPanel = new Panel();
	    historyListingPanel.setLayoutManager(new GridLayout(4));
	    GridLayout gridLayout = (GridLayout)historyListingPanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);
	    historyListingPanel.addComponent(new Label("Invoice No.").addStyle(SGR.BOLD));
	    historyListingPanel.addComponent(new Label("Amount").addStyle(SGR.BOLD));
	    historyListingPanel.addComponent(new Label("Payment Method").addStyle(SGR.BOLD));
	    historyListingPanel.addComponent(new Label("Paid on").addStyle(SGR.BOLD));
	    new PaymentHistoryRetriever();
	    HashMap<String, PaymentHistory> historyHashmap = PaymentHistoryRetriever.paymentHistories;
	    List<PaymentHistory> paymentHistories = new ArrayList<PaymentHistory>(historyHashmap.values());
	    String userId = UserSelection.user.getUserId().toString();
	    paymentHistories.stream()
	    .filter(h -> h.getId().equals(userId))
	    .forEach(h -> {
	    	historyListingPanel.addComponent(new Label(h.getInvoiceId()));
	    	historyListingPanel.addComponent(new Label("$" + h.getInvoiceAmount()));
	    	historyListingPanel.addComponent(new Label(h.getPaymentMethod()));
	    	historyListingPanel.addComponent(new Label(h.getTimestamp()));
	    });
	    panel.addComponent(historyListingPanel);
	    menuWindow.setComponent(panel);
	    gui.addWindowAndWait(menuWindow);
	}
}
