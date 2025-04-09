package org.example.CarMgmt.Billing.Invoices;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import org.example.CarMgmt.App;
import org.example.CarMgmt.InvoiceStatuses;
import org.example.CarMgmt.PredefinedPenalties;
import org.example.CarMgmt.Beans.Invoice;
import org.example.CarMgmt.Beans.PaymentHistory;
import org.example.CarMgmt.Beans.Penalty;
import org.example.CarMgmt.Billing.Payments.PaymentHistoryRetriever;
import org.example.CarMgmt.Billing.Payments.PaymentHistoryWriter;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Interactable.FocusChangeDirection;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.RadioBoxList;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.table.Table;

public class InvoiceStatusUpdater {
	static public void showInvoiceStatusUpdater(Invoice invoice) {
		String invoiceId = invoice.getId();
		String customerId = invoice.getUserId();
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Update Invoice #%s Status", invoiceId));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
		panel.addComponent(new Label("Status").addStyle(SGR.BOLD));
		//TextBox descriptionInput = new TextBox(new TerminalSize(30, 1));
		//panel.addComponent(descriptionInput);
		ComboBox<String> comboBox = new ComboBox<String>();
		List<String> invoiceStatuses = new InvoiceStatuses().getInvoiceStatuses();
		for (String s: invoiceStatuses) {
			comboBox.addItem(s);
		}
		comboBox.setSelectedItem(invoice.getStatus());
		panel.addComponent(comboBox);
		panel.addComponent(new EmptySpace());
		Panel paidViaPanel = new Panel();
		paidViaPanel.setLayoutManager(new GridLayout(1));
		TerminalSize size = new TerminalSize(18, 3);
		Label lockedInAmountDateLabel = new Label(String.format("Locked In Amount on %s", invoice.getLockedInDate())).addStyle(SGR.BOLD);
		String lockedInAmount = invoice.getLockedInAmount();
		Label lockedInAmountLabel = new Label(String.format("$%s", lockedInAmount));
		EmptySpace lockedInPaidViaSpacer = new EmptySpace();
		Label paidViaLabel = new Label("Paid via").addStyle(SGR.BOLD);
		RadioBoxList<String> paidViaRadio = new RadioBoxList<String>(size);
		paidViaRadio.addItem("Bank Transfer");
		paidViaRadio.addItem("Cheque");
		paidViaRadio.addItem("Cash");
		paidViaRadio.setCheckedItemIndex(0);
		comboBox.addListener((selectedIndex, previousSelection, changedByUserInteraction) -> {
			if (selectedIndex == 1) {
				paidViaPanel.addComponent(lockedInAmountDateLabel);
				paidViaPanel.addComponent(lockedInAmountLabel);
				paidViaPanel.addComponent(lockedInPaidViaSpacer);
				paidViaPanel.addComponent(paidViaLabel);
				paidViaPanel.addComponent(paidViaRadio);
			} else {
				paidViaPanel.removeComponent(lockedInAmountDateLabel);
				paidViaPanel.removeComponent(lockedInAmountLabel);
				paidViaPanel.removeComponent(lockedInPaidViaSpacer);
				paidViaPanel.removeComponent(paidViaLabel);
				paidViaPanel.removeComponent(paidViaRadio);
			}
		});
		panel.addComponent(paidViaPanel);
		panel.addComponent(new EmptySpace());
		Panel ctaPanel = new Panel();
		ctaPanel.setLayoutManager(new GridLayout(2));
		Button editStatus = new Button("Edit Status", () -> {
			try {
				String selectedStatus = comboBox.getText();
				invoice.setStatus(selectedStatus);
				String selectedPaymentMethod = paidViaRadio.getCheckedItem();
				Integer lastPaymentHistoryId = Integer.parseInt(PaymentHistoryRetriever.currentLastRowId);
				Integer offset = lastPaymentHistoryId + 1;
				LocalDateTime currentTime = LocalDateTime.now();
				if (selectedStatus.equals("Completed")) {
					PaymentHistory paymentHistory = new PaymentHistory(offset.toString(), invoiceId, customerId, lockedInAmount, selectedPaymentMethod, currentTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
					try {
						new PaymentHistoryWriter().writeToCsv(paymentHistory);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				new MessageDialogBuilder()
	    		.setTitle("")
	    		.setText("Invoice Status Updated Successfully")
	    		.build()
	    		.showDialog(gui);
				menuWindow.close();
				InvoiceSearch.showInvoiceSearchForm();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			});
		ctaPanel.addComponent(editStatus);
		Button cancel = new Button("Cancel", () -> {
			menuWindow.close();
			InvoiceSearch.showInvoiceSearchForm();
		});
		ctaPanel.addComponent(cancel);
		panel.addComponent(ctaPanel);
		menuWindow.setComponent(panel);
	    gui.addWindowAndWait(menuWindow);
	}
}
