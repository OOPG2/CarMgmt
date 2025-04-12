package billing.payment.method;

import app.*;
import constants.*;
import beans.*;

import billing.invoice.*;
import billing.payment.*;

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
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

import io.nayuki.qrcodegen.QrCode;
import objects.Customer;
import rewards.AddPoints;

public class PayNow {
	public static void showPayNowForm(Invoice invoice, Double totalPayable) {
		String invoiceId = invoice.getId();
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("PayNow Payment"));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
        Button back = new Button("Back", () -> {
        	menuWindow.close();
        	new InvoiceViewer().showInvoice(invoice);
        });
        panel.addComponent(back);
        String userId = App.authenticationManager.getLoggedUser().getUserId();
        Button transferred = new Button("Simulate Payment", () -> {
        	LocalDate lockedInDate = LocalDate.now();
        	invoice.setLockedInAmount(String.format("%.2f", totalPayable));
        	invoice.setLockedInDate(lockedInDate.format(DateTimeFormatter.ISO_DATE));
        	invoice.setStatus("Completed");
			InvoiceEditor.modifyRowInCsv(invoiceId, invoice);
			new PaymentHistoryRetriever();
			Integer lastPaymentHistoryId = Integer.parseInt(PaymentHistoryRetriever.currentLastRowId);
			Integer paymentHistoryOffset = lastPaymentHistoryId + 1;
			LocalDateTime currentTime = LocalDateTime.now();
			PaymentHistory paymentHistory = new PaymentHistory(paymentHistoryOffset.toString(), invoiceId, userId, String.format("%.2f", totalPayable), "PayNow", currentTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
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
        panel.addComponent(transferred);
        Panel formPanel = new Panel();
        Constants constants = new Constants();
        formPanel.setLayoutManager(new GridLayout(2));
        formPanel.addComponent(new Label("Invoice No."));
        formPanel.addComponent(new Label(invoiceId));
        formPanel.addComponent(new Label("Total Payable"));
        formPanel.addComponent(new Label(String.format("$%.2f", totalPayable)));
        formPanel.addComponent(new EmptySpace());
        formPanel.addComponent(new EmptySpace());
        formPanel.addComponent(new Label("PayNow ID (UEN)"));
        formPanel.addComponent(new Label(constants.getUEN()));
        formPanel.addComponent(new Label("Payment Ref."));
        formPanel.addComponent(new Label(invoiceId));
        panel.addComponent(formPanel);
        QrCode qr = QrCode.encodeText("00020101021126510009SG.PAYNOW010120210200604393R030110510TAISP FUND52040000530370254091000000.05802SG5902NA6009Singapore62140110TAISP FUND630421A9", QrCode.Ecc.LOW);
        int size = qr.size;
        QRComponent qrComponent = new QRComponent(qr);
        panel.addComponent(qrComponent);
        panel.addComponent(new EmptySpace());
        menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
	}
}
