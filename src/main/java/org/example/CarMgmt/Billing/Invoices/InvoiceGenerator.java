package org.example.CarMgmt.Billing.Invoices;

import java.util.ArrayList;
import java.util.List;

import org.example.CarMgmt.App;
import org.example.CarMgmt.Beans.Penalty;
import org.example.CarMgmt.Beans.Reservation;
import org.example.CarMgmt.Billing.MainMenu;
import org.example.CarMgmt.Billing.User;
import org.example.CarMgmt.Billing.UserSelection;
import org.example.CarMgmt.Billing.Payments.InvoiceRetriever;
import org.example.CarMgmt.Billing.Payments.InvoiceViewer;
import org.example.CarMgmt.Billing.Payments.ReservationRetriever;
import org.example.CarMgmt.Exceptions.RowNotFoundException;
import org.example.CarMgmt.Rewards.TierBenefits;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.table.Table;

public class InvoiceGenerator {
	public static List<Penalty> penalties = new ArrayList<>();
	public static BasicWindow menuWindow = new BasicWindow(String.format("Generate Invoice"));
	public void showInvoiceGenerator(Reservation reservation) {
		MultiWindowTextGUI gui = App.gui;
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
	    Button back = new Button("Back", () -> {
	    	menuWindow.close();
	    	MainMenu.showUserSelection();
	    });
	    panel.addComponent(back);
	    Panel invoiceForm = new Panel();
	    invoiceForm.setLayoutManager(new GridLayout(2));
	    invoiceForm.addComponent(new Label("Reservation No.:"));
	    invoiceForm.addComponent(new Label(reservation.getId()));
	    invoiceForm.addComponent(new EmptySpace());
	    invoiceForm.addComponent(new EmptySpace());
	    invoiceForm.addComponent(new Label("Invoice Items").addStyle(SGR.UNDERLINE).addStyle(SGR.BOLD));
	    invoiceForm.addComponent(new EmptySpace());
	    invoiceForm.addComponent(new Label("Rental"));
	    
	    invoiceForm.addComponent(new Label(""));
	    UserRetriever userRetriever = new UserRetriever();
	    User customer = userRetriever.retrieveById(reservation.getUserId());
	    System.out.print(customer);
	    TierBenefits tierBenefits = new TierBenefits(customer.getLifetimePoints());
	    String insurance = reservation.getInsurance();
	    if (tierBenefits.freeInsurance()) {
	    	insurance = "WAIVED";
	    }
	    invoiceForm.addComponent(new Label("Insurance"));
	    invoiceForm.addComponent(new Label(insurance));
	    invoiceForm.addComponent(new EmptySpace());
	    invoiceForm.addComponent(new EmptySpace());
	    panel.addComponent(invoiceForm);
	    Panel penaltyForm = new Panel();
	    penaltyForm.setLayoutManager(new GridLayout(1));
	    Table<String> penaltyTable = new Table<String>("Penalties", "");
	    String addPenalty = "+ Add Penalty";
	    penaltyTable.getTableModel().addRow("+ Add Penalty", "");
	    penaltyTable.setSelectAction(() -> {
    		List<String> row = penaltyTable.getTableModel().getRow(penaltyTable.getSelectedRow());
    		if (row.get(0).equals(addPenalty)) {
    			new PenaltyCreator().showPenaltyCreator(penaltyTable);
    		} else {
    			
    		}
    	});
	    penaltyForm.addComponent(penaltyTable);
	    panel.addComponent(penaltyForm);
	    Button generateInvoice = new Button("Generate Invoice", () -> {
	    	
	    });
	    panel.addComponent(new EmptySpace());
	    panel.addComponent(generateInvoice);
	    menuWindow.setComponent(panel);
	    gui.addWindowAndWait(menuWindow);
	}
}
