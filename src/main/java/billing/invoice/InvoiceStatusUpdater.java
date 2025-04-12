package billing.invoice;

import app.App;
import beans.Invoice;
import beans.PaymentHistory;
import billing.payment.PaymentHistoryRetriever;
import billing.payment.PaymentHistoryWriter;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import constants.InvoiceStatuses;
import manager.UserManager;
import objects.Customer;
import rewards.AddPoints;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class InvoiceStatusUpdater {
	static public void showInvoiceStatusUpdater(Invoice invoice) {
		App app = new App();
		UserManager userManager = app.getUserManager();

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
				if (!lockedInAmount.isEmpty()) {
					paidViaPanel.addComponent(lockedInAmountDateLabel);
					paidViaPanel.addComponent(lockedInAmountLabel);
					paidViaPanel.addComponent(lockedInPaidViaSpacer);
					paidViaPanel.addComponent(paidViaLabel);
					paidViaPanel.addComponent(paidViaRadio);
				} else {
					comboBox.setSelectedIndex(previousSelection);
					new MessageDialogBuilder()
		    		.setTitle("")
		    		.setText("Customer has not selected their payment method!")
		    		.build()
		    		.showDialog(gui);
				}
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
				new PaymentHistoryRetriever();
				Integer lastPaymentHistoryId = Integer.parseInt(PaymentHistoryRetriever.currentLastRowId);
				Integer paymentHistoryOffset = lastPaymentHistoryId + 1;
				LocalDateTime currentTime = LocalDateTime.now();
				if (selectedStatus.equals("Completed")) {
					PaymentHistory paymentHistory = new PaymentHistory(paymentHistoryOffset.toString(), invoiceId, customerId, lockedInAmount, selectedPaymentMethod, currentTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
					try {
						new PaymentHistoryWriter().writeToCsv(paymentHistory);
						Integer pointsEarned = (int) Double.parseDouble(invoice.getBaseAmount());
						Customer customer = (Customer) userManager.getUserByID(customerId);
						AddPoints.addPoints(customer, pointsEarned);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				InvoiceEditor.modifyRowInCsv(invoiceId, invoice);
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
