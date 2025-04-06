package org.example.CarMgmt.Billing.Payments.Methods;

import org.example.CarMgmt.App;
import org.example.CarMgmt.Beans.Invoice;
import org.example.CarMgmt.Billing.Payments.InvoiceSelector;
import org.example.CarMgmt.Billing.Payments.InvoiceViewer;

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
	public void showCreditCardForm(Invoice invoice, Double totalPayable) {
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
        formPanel.addComponent(new Label(invoice.getId()));
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
        Button pay = new Button("Pay", () -> {
        	new MessageDialogBuilder()
    		.setTitle("")
    		.setText("Payment Successful")
    		.build()
    		.showDialog(gui);
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
