package org.example.CarMgmt.Billing.Payments.Methods;

import org.example.CarMgmt.App;
import org.example.CarMgmt.Constants;
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
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

public class BankTransfer {
	public void showBankTransferForm(Invoice invoice, Double totalPayable) {
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Bank Transfer Payment"));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
        Button back = new Button("Back", () -> {
        	menuWindow.close();
        	new InvoiceViewer().showInvoice(invoice);
        });
        panel.addComponent(back);
        Panel formPanel = new Panel();
        Constants constants = new Constants();
        formPanel.setLayoutManager(new GridLayout(2));
        formPanel.addComponent(new Label("Invoice No."));
        formPanel.addComponent(new Label(invoice.getId()));
        formPanel.addComponent(new Label("Total Payable"));
        formPanel.addComponent(new Label(String.format("$%.2f", totalPayable)));
        formPanel.addComponent(new EmptySpace());
        formPanel.addComponent(new EmptySpace());
        formPanel.addComponent(new Label("Recipient"));
        formPanel.addComponent(new Label(constants.getBankRecipient()));
        formPanel.addComponent(new Label("Bank"));
        formPanel.addComponent(new Label(constants.getBankName()));
        formPanel.addComponent(new Label("Account No."));
        formPanel.addComponent(new Label(constants.getBankAccountNo()));
        formPanel.addComponent(new Label("Payment Ref."));
        formPanel.addComponent(new Label(invoice.getId()));
        panel.addComponent(formPanel);
        panel.addComponent(new EmptySpace());
        Button transferred = new Button("I have transferred", () -> {
        	new MessageDialogBuilder()
    		.setTitle("")
    		.setText("Your payment status will be updated within 3 working days.")
    		.build()
    		.showDialog(gui);
        });
        panel.addComponent(transferred);
        menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
	}
}
