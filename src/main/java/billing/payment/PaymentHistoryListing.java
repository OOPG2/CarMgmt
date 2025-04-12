package billing.payment;

import app.App;
import beans.PaymentHistory;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.*;
import manager.AuthenticationManager;
import menus.LoggedMenu;
import objects.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaymentHistoryListing {
	static public void showPaymentHistoryListing() {
		App app = new App();
		AuthenticationManager authenticationManager = app.getAuthenticationManager();
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Payment Histories"));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
	    Button back = new Button("Back", () -> {
	    	menuWindow.close();
	    	LoggedMenu.showLoggedMenu();
	    });
	    panel.addComponent(back);
	    Panel historyListingPanel = new Panel();
	    historyListingPanel.setLayoutManager(new GridLayout(4));
	    GridLayout gridLayout = (GridLayout)historyListingPanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);
	    historyListingPanel.addComponent(new Label("Invoice No.").addStyle(SGR.BOLD));
	    historyListingPanel.addComponent(new Label("Amount").addStyle(SGR.BOLD));
	    historyListingPanel.addComponent(new Label("Payment Method").addStyle(SGR.BOLD));
	    historyListingPanel.addComponent(new Label("Confirmed At").addStyle(SGR.BOLD));
	    new PaymentHistoryRetriever();
	    HashMap<String, PaymentHistory> historyHashmap = PaymentHistoryRetriever.paymentHistories;
	    List<PaymentHistory> paymentHistories = new ArrayList<PaymentHistory>(historyHashmap.values());
        User loggedUser = authenticationManager.getLoggedUser();
	    String userId = loggedUser.getUserId();
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
