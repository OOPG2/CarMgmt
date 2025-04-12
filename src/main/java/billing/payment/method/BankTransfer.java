package billing.payment.method;

import app.*;
import constants.*;
import beans.*;
import billing.invoice.*;
import billing.payment.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

public class BankTransfer {
	public static void showBankTransferForm(Invoice invoice, Double totalPayable) {
		String invoiceId = invoice.getId();
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Bank Transfer / In-Person Payment"));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
        Button back = new Button("Back", () -> {
        	menuWindow.close();
        	InvoiceViewer.showInvoice(invoice);
        });
        panel.addComponent(back);
        Panel formPanel = new Panel();
        formPanel.setLayoutManager(new GridLayout(2));
        formPanel.addComponent(new Label("Invoice No."));
        formPanel.addComponent(new Label(invoiceId));
        formPanel.addComponent(new Label("Total Payable"));
        formPanel.addComponent(new Label(String.format("$%.2f", totalPayable)));
        formPanel.addComponent(new EmptySpace());
        formPanel.addComponent(new EmptySpace());
        formPanel.addComponent(new Label("Recipient"));
        formPanel.addComponent(new Label(Constants.getBankRecipient()));
        formPanel.addComponent(new Label("Bank"));
        formPanel.addComponent(new Label(Constants.getBankName()));
        formPanel.addComponent(new Label("Account No."));
        formPanel.addComponent(new Label(Constants.getBankAccountNo()));
        formPanel.addComponent(new Label("Payment Ref."));
        formPanel.addComponent(new Label(invoiceId));
        panel.addComponent(formPanel);
        panel.addComponent(new EmptySpace());
        Button transferred = new Button("I have transferred / I am paying in person TODAY", () -> {
        	LocalDate lockedInDate = LocalDate.now();
        	invoice.setLockedInAmount(String.format("%.2f", totalPayable));
        	invoice.setLockedInDate(lockedInDate.format(DateTimeFormatter.ISO_DATE));
        	InvoiceEditor.modifyRowInCsv(invoiceId, invoice);
			Integer lastPaymentHistoryId = Integer.parseInt(PaymentHistoryRetriever.currentLastRowId);
        	new MessageDialogBuilder()
    		.setTitle("")
    		.setText("Your payment status will be updated within 3 working days (for bank transfer)\nor upon in-person payment.")
    		.build()
    		.showDialog(gui);
        	menuWindow.close();
			new InvoiceSelector().showInvoiceSelector();
        });
        panel.addComponent(transferred);
        menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
	}
}
