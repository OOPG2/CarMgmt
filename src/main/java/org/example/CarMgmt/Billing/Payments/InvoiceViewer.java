package org.example.CarMgmt.Billing.Payments;

import org.example.CarMgmt.App;
import org.example.CarMgmt.Constants;
import org.example.CarMgmt.Beans.Invoice;
import org.example.CarMgmt.Beans.Penalty;
import org.example.CarMgmt.Beans.Reservation;
import org.example.CarMgmt.Billing.Payments.Methods.BankTransfer;
import org.example.CarMgmt.Billing.Payments.Methods.CreditCard;
import org.example.CarMgmt.Billing.Payments.Methods.PayNow;
import org.example.CarMgmt.Helper.TotalRentalCalculator;

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
	public Double totalPayable = 100.0;
	public void showInvoice(Invoice invoice) {
		Constants constants = new Constants();
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Invoice #%s", invoice.getId()));
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
        	new CreditCard().showCreditCardForm(invoice, totalPayable);
        });
        Button payNow = new Button("PayNow", () -> {
        	menuWindow.close();
        	new PayNow().showPayNowForm(invoice, totalPayable);
        });
        Button bankTransfer = new Button("Bank Transfer", () -> {
        	menuWindow.close();
        	new BankTransfer().showBankTransferForm(invoice, totalPayable);
        });
        paymentMethodsPanel.addComponent(creditCard);
        paymentMethodsPanel.addComponent(payNow);
        paymentMethodsPanel.addComponent(bankTransfer);
        panel.addComponent(paymentMethodsPanel);
        panel.addComponent(new EmptySpace());
        Panel invoiceDetailsPanel = new Panel(new GridLayout(2));
        GridLayout gridLayout = (GridLayout)invoiceDetailsPanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);
        invoiceDetailsPanel.addComponent(new Label("Description").addStyle(SGR.BOLD));
        invoiceDetailsPanel.addComponent(new Label("Amount").addStyle(SGR.BOLD));
        ReservationRetriever reservationRetriever = new ReservationRetriever();
    	Reservation reservation = reservationRetriever.retrieveById(invoice.getReservationId());
    	Double totalRental = new TotalRentalCalculator().calculateRental(reservation.getDailyRental());
    	// car rental
        invoiceDetailsPanel.addComponent(new Label("Vehicle Rental"));
        invoiceDetailsPanel.addComponent(new Label(String.format("%.2f", totalRental)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
        // insurance
        Double insurance = Double.parseDouble(reservation.getInsurance());
        invoiceDetailsPanel.addComponent(new Label("Insurance"));
        invoiceDetailsPanel.addComponent(new Label(String.format("%.2f", insurance)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
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
        Double totalOverdueFine = invoice.getTotalOverdueFine();
        Long overdueDays = invoice.getOverdueDays();
        if (totalOverdueFine > 0) {
        	invoiceDetailsPanel.addComponent(new Label(String.format("Overdue Fine (%d day%s)", overdueDays, overdueDays > 1 ? "s" : "")));
            invoiceDetailsPanel.addComponent(new Label(String.format("%.2f", totalOverdueFine)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
        }
        
        // spacer
        invoiceDetailsPanel.addComponent(new EmptySpace());
        invoiceDetailsPanel.addComponent(new EmptySpace());
        // subtotal
        invoiceDetailsPanel.addComponent(new Label("Subtotal").addStyle(SGR.BOLD));
        invoiceDetailsPanel.addComponent(new Label(String.format("%.2f", Double.parseDouble(invoice.getSubtotal()) + totalOverdueFine)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
        // gst
        Double gstRate = constants.getGSTRate();
        invoiceDetailsPanel.addComponent(new Label(String.format("GST (%.0f%%)", gstRate*100)).addStyle(SGR.BOLD));
        invoiceDetailsPanel.addComponent(new Label(String.format("%.2f", invoice.getGST())).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
        // discounts
        invoiceDetailsPanel.addComponent(new Label("Discounts").addStyle(SGR.BOLD));
        invoiceDetailsPanel.addComponent(new Label(""));
        // total payable
        invoiceDetailsPanel.addComponent(new Label("Total Payable").addStyle(SGR.BOLD));
        invoiceDetailsPanel.addComponent(new Label(""));

        
        panel.addComponent(invoiceDetailsPanel);
    	try {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
	}
}
