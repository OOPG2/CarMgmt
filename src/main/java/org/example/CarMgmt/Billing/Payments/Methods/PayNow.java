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

import io.nayuki.qrcodegen.QrCode;

public class PayNow {
	public void showPayNowForm(Invoice invoice, Double totalPayable) {
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("PayNow Payment"));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
        Button back = new Button("Back", () -> {
        	menuWindow.close();
        	new InvoiceViewer().showInvoice(invoice);
        });
        panel.addComponent(back);
        Button transferred = new Button("Simulate Payment", () -> {
        	new MessageDialogBuilder()
    		.setTitle("")
    		.setText("Payment Successful")
    		.build()
    		.showDialog(gui);
        });
        panel.addComponent(transferred);
        Panel formPanel = new Panel();
        Constants constants = new Constants();
        formPanel.setLayoutManager(new GridLayout(2));
        formPanel.addComponent(new Label("Invoice No."));
        formPanel.addComponent(new Label(invoice.getId()));
        formPanel.addComponent(new Label("Total Payable"));
        formPanel.addComponent(new Label(String.format("$%.2f", totalPayable)));
        formPanel.addComponent(new EmptySpace());
        formPanel.addComponent(new EmptySpace());
        formPanel.addComponent(new Label("PayNow ID (UEN)"));
        formPanel.addComponent(new Label(constants.getUEN()));
        formPanel.addComponent(new Label("Payment Ref."));
        formPanel.addComponent(new Label(invoice.getId()));
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
