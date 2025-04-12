package billing.payment.method;

import app.*;
import beans.*;

import billing.invoice.*;
import billing.payment.*;
import objects.Customer;
import rewards.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

public class CreditCard {
	public static void showCreditCardForm(Invoice invoice, Double totalPayable) {
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Credit Card Payment"));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
        Button back = new Button("Back", () -> {
        	menuWindow.close();
        	new InvoiceViewer().showInvoice(invoice);
        });
        panel.addComponent(back);
        Panel formPanel = new Panel();
        formPanel.setLayoutManager(new GridLayout(2));
        formPanel.addComponent(new Label("Invoice No."));
        String invoiceId = invoice.getId();
        formPanel.addComponent(new Label(invoiceId));
        formPanel.addComponent(new Label("Total Payable"));
        formPanel.addComponent(new Label(String.format("$%.2f", totalPayable)));
        formPanel.addComponent(new EmptySpace());
        formPanel.addComponent(new EmptySpace());
        formPanel.addComponent(new Label("Credit Card Number"));
        TextBox ccNoInput = new TextBox(new TerminalSize(20, 1), "4545454545454545");
        formPanel.addComponent(ccNoInput);
        formPanel.addComponent(new Label("Expiration (MMYY)"));
        TextBox expirationInput = new TextBox(new TerminalSize(5, 1), "1230");
        formPanel.addComponent(expirationInput);
        formPanel.addComponent(new Label("CVV"));
        TextBox cvvInput = new TextBox(new TerminalSize(5, 1), "123");
        formPanel.addComponent(cvvInput);
        String userId = App.authenticationManager.getLoggedUser().getUserId();
        Button pay = new Button("Pay", () -> {
        	LocalDate lockedInDate = LocalDate.now();
        	invoice.setLockedInAmount(String.format("%.2f", totalPayable));
        	invoice.setLockedInDate(lockedInDate.format(DateTimeFormatter.ISO_DATE));
        	invoice.setStatus("Completed");
			InvoiceEditor.modifyRowInCsv(invoiceId, invoice);
			new PaymentHistoryRetriever();
			Integer lastPaymentHistoryId = Integer.parseInt(PaymentHistoryRetriever.currentLastRowId);
			Integer paymentHistoryOffset = lastPaymentHistoryId + 1;
			LocalDateTime currentTime = LocalDateTime.now();
			PaymentHistory paymentHistory = new PaymentHistory(paymentHistoryOffset.toString(), invoiceId, userId, String.format("%.2f", totalPayable), "Credit Card", currentTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
			try {
				new PaymentHistoryWriter().writeToCsv(paymentHistory);
				Integer pointsEarned = (int) Double.parseDouble(invoice.getBaseAmount());
				Customer customer = (Customer) App.authenticationManager.getLoggedUser();
				AddPoints.addPoints(customer, pointsEarned);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	new MessageDialogBuilder()
    		.setTitle("")
    		.setText("Payment Successful")
    		.build()
    		.showDialog(gui);
			menuWindow.close();
			new InvoiceSelector().showInvoiceSelector();
        });
        formPanel.addComponent(pay);
        Button clear = new Button("Clear", () -> {
        	ccNoInput.setText("");
        	expirationInput.setText("");
        	cvvInput.setText("");
        });
        formPanel.addComponent(clear);
        panel.addComponent(formPanel);
        menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
	}
}
