package billing.invoice;

import app.App;
import beans.Invoice;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import menus.LoggedMenu;

import java.util.List;

public class InvoiceSelector {
	public static Table<String> toPayTable;
	public static Table<String> pastInvoicesTable;
	public static Invoice selectedInvoice;
	public static void showInvoiceSelector() {
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow("Select invoice to view");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));
        Button back = new Button("Back", () -> {
        	menuWindow.close();
        	LoggedMenu.showLoggedMenu();
        });
        panel.addComponent(back);
		panel.addComponent(new Label("To Pay"));
		toPayTable = new Table<String>("Invoice No.", "Reservation No.", "Total Amount", "Due Date");
    	panel.addComponent(toPayTable);
		panel.addComponent(new EmptySpace());
		panel.addComponent(new Label("Past Invoices"));
		pastInvoicesTable = new Table<String>("Invoice No.", "Reservation No.", "Total Amount");
		panel.addComponent(pastInvoicesTable);
		try {
			new InvoicesRowPopulator().populateInvoices();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toPayTable.setSelectAction(() -> {
			List<String> toPayTableRow = toPayTable.getTableModel().getRow(toPayTable.getSelectedRow());
			menuWindow.close();
			new InvoiceViewer().showInvoice(InvoiceRetriever.invoices.get(toPayTableRow.get(0)));
		});
		pastInvoicesTable.setSelectAction(() -> {
			List<String> pastInvoicesTableRow = pastInvoicesTable.getTableModel().getRow(pastInvoicesTable.getSelectedRow());
			menuWindow.close();
			System.out.println(pastInvoicesTableRow.get(0));
			new InvoiceViewer().showInvoice(InvoiceRetriever.invoices.get(pastInvoicesTableRow.get(0)));
		});
		menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
	}
}
