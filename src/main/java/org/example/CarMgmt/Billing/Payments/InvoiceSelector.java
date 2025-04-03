package org.example.CarMgmt.Billing.Payments;

import java.util.List;

import org.example.CarMgmt.App;
import org.example.CarMgmt.Beans.Invoice;
import org.example.CarMgmt.Billing.MainMenu;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.table.Table;

public class InvoiceSelector {
	public static Table<String> table;
	public static Invoice selectedInvoice;
	public void showInvoiceSelector() {
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow("Select invoice to pay");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));
        Button back = new Button("Back", () -> {
        	menuWindow.close();
        	MainMenu.showUserSelection();
        });
        panel.addComponent(back);
    	table = new Table<String>("Invoice No.", "Reservation No.", "Total Amount", "Due");
    	try {
			new InvoicesRowPopulator().populateInvoices();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	table.setSelectAction(() -> {
    		List<String> row = table.getTableModel().getRow(table.getSelectedRow());
    		menuWindow.close();
    		new InvoiceViewer().showInvoice();
    	});
    	panel.addComponent(table);
    	menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
	}
}
