package billing.invoice;

import Reservations.ReservationManager;
import app.*;
import constants.*;
import beans.*;
import billing.payment.method.*;
import billing.penalty.*;
import exceptions.*;
import billing.helper.*;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.table.Table;

public class InvoiceViewer {
	public static Table<String> table;
	public static Double totalPayable = 0.0;
	public static void showInvoice(Invoice invoice) {
		try {
			MultiWindowTextGUI gui = App.gui;
			BasicWindow menuWindow = new BasicWindow(String.format("Invoice #%s", invoice.getId()));
	        Panel panel = new Panel();
	        panel.setLayoutManager(new GridLayout(1));
	        Button back = new Button("Back", () -> {
	        	menuWindow.close();
	        	new InvoiceSelector().showInvoiceSelector();
	        });
	        panel.addComponent(back);
			if (invoice.getStatus().equalsIgnoreCase("pending")) {
				panel.addComponent(new Label("Pay via:"));
				Panel paymentMethodsPanel = new Panel();
				paymentMethodsPanel.setLayoutManager(new GridLayout(4));
				Button creditCard = new Button("Credit Card", () -> {
					menuWindow.close();
					CreditCard.showCreditCardForm(invoice, totalPayable);
				});
				Button payNow = new Button("PayNow", () -> {
					menuWindow.close();
					PayNow.showPayNowForm(invoice, totalPayable);
				});
				Button bankTransfer = new Button("Bank Transfer / " +
						"Pay In-Person", () -> {
					menuWindow.close();
					BankTransfer.showBankTransferForm(invoice, totalPayable);
				});
				paymentMethodsPanel.addComponent(creditCard);
				paymentMethodsPanel.addComponent(payNow);
				paymentMethodsPanel.addComponent(bankTransfer);
				panel.addComponent(paymentMethodsPanel);
			}
	        panel.addComponent(new EmptySpace());
	        Panel invoiceDetailsPanel = new Panel(new GridLayout(2));
	        GridLayout gridLayout = (GridLayout)invoiceDetailsPanel.getLayoutManager();
	        gridLayout.setHorizontalSpacing(3);
	        invoiceDetailsPanel.addComponent(new Label("Description").addStyle(SGR.BOLD));
	        invoiceDetailsPanel.addComponent(new Label("Amount ($)").addStyle(SGR.BOLD));
	        //ReservationRetriever reservationRetriever = new ReservationRetriever();
			ReservationManager reservationManager = App.reservationManager;
	    	Reservations.Reservation reservation = reservationManager.getReservationById(Integer.parseInt(invoice.getReservationId())); //reservationRetriever.retrieveById(invoice.getReservationId());
	    	Double totalRental = new TotalRentalCalculator().calculateRental(reservation.getDailyRental());
	    	// car rental
	        invoiceDetailsPanel.addComponent(new Label("Vehicle Rental"));
	        invoiceDetailsPanel.addComponent(new Label(String.format("%.2f", totalRental)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
	        // insurance
	        Double insurance = Double.parseDouble(reservation.getInsurance());
	        invoiceDetailsPanel.addComponent(new Label("Insurance"));
	        invoiceDetailsPanel.addComponent(new Label(String.format("%.2f", insurance)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
	        // redeemed points
	        Integer redeemedDollarAmount = Integer.parseInt(invoice.getRedeemedDollarAmount());
	        if (redeemedDollarAmount > 0) {
	        	invoiceDetailsPanel.addComponent(new Label(String.format("Redeemed %d Points", redeemedDollarAmount*100)));
		        invoiceDetailsPanel.addComponent(new Label(String.format("-%d.00", redeemedDollarAmount)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
	        }
	        // penalties
	        String penaltyIds = invoice.getPenaltyIds();
	        if (!penaltyIds.isEmpty()) {
	        	String[] splitPenaltyIds = penaltyIds.split(",");
	        	PenaltyRetriever penaltyRetriever = new PenaltyRetriever();
	        	for (String id: splitPenaltyIds) {
	        		Penalty penalty = penaltyRetriever.retrieveById(id);
	        		invoiceDetailsPanel.addComponent(new Label(String.format("Penalty: %s", penalty.getDescription())));
	                invoiceDetailsPanel.addComponent(new Label(String.format("%.2f", Double.parseDouble(penalty.getAmount()))).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
	        	}
	        }
	        // overdue fine
	        Double totalOverdueFine = invoice.getTotalOverdueFine() != null ? invoice.getTotalOverdueFine() : 0.0;
	        Long overdueDays = invoice.getOverdueDays();
	        if (totalOverdueFine > 0) {
	        	invoiceDetailsPanel.addComponent(new Label(String.format("Overdue Fine (%d day%s)", overdueDays, overdueDays > 1 ? "s" : "")));
	            invoiceDetailsPanel.addComponent(new Label(String.format("%.2f", totalOverdueFine)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
	        }
	        
	        // spacer
	        invoiceDetailsPanel.addComponent(new EmptySpace());
	        invoiceDetailsPanel.addComponent(new EmptySpace());
	        // subtotal
	        Double subtotal = Double.parseDouble(invoice.getSubtotal()) + totalOverdueFine;
	        invoiceDetailsPanel.addComponent(new Label("Subtotal").addStyle(SGR.BOLD));
	        invoiceDetailsPanel.addComponent(new Label(String.format("%.2f", Double.parseDouble(invoice.getSubtotal()) + totalOverdueFine)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
	        // gst
	        Double gstRate = Constants.getGSTRate();
	        invoiceDetailsPanel.addComponent(new Label(String.format("GST (%.0f%%)", gstRate*100)).addStyle(SGR.BOLD));
	        invoiceDetailsPanel.addComponent(new Label(String.format("%.2f", subtotal*gstRate)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
	        // total payable
	        totalPayable = subtotal * (1 + gstRate);
	        invoiceDetailsPanel.addComponent(new Label("Total Payable").addStyle(SGR.BOLD));
	        invoiceDetailsPanel.addComponent(new Label(String.format("%.2f", totalPayable)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
	        panel.addComponent(invoiceDetailsPanel);
	    	menuWindow.setComponent(panel);
	        gui.addWindowAndWait(menuWindow);
		} catch (RowNotFoundException e) {
			e.printStackTrace();
		};
	}
}
