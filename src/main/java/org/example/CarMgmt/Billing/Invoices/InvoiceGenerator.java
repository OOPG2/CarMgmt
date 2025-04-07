package org.example.CarMgmt.Billing.Invoices;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.example.CarMgmt.App;
import org.example.CarMgmt.Beans.Invoice;
import org.example.CarMgmt.Beans.Penalty;
import org.example.CarMgmt.Beans.Reservation;
import org.example.CarMgmt.Billing.MainMenu;
import org.example.CarMgmt.Billing.User;
import org.example.CarMgmt.Billing.UserSelection;
import org.example.CarMgmt.Billing.Payments.InvoiceRetriever;
import org.example.CarMgmt.Billing.Payments.InvoiceViewer;
import org.example.CarMgmt.Billing.Payments.InvoiceWriter;
import org.example.CarMgmt.Billing.Payments.PenaltyRetriever;
import org.example.CarMgmt.Billing.Payments.PenaltyWriter;
import org.example.CarMgmt.Billing.Payments.ReservationRetriever;
import org.example.CarMgmt.Exceptions.RowNotFoundException;
import org.example.CarMgmt.Helper.TotalRentalCalculator;
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
	String reservationId;
	String customerId;
	Double baseTotal = 0.0;
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
	    reservationId = reservation.getId();
	    invoiceForm.addComponent(new Label("Reservation No.:"));
	    invoiceForm.addComponent(new Label(reservationId));
	    invoiceForm.addComponent(new EmptySpace());
	    invoiceForm.addComponent(new EmptySpace());
	    invoiceForm.addComponent(new Label("Base Items").addStyle(SGR.UNDERLINE).addStyle(SGR.BOLD));
	    invoiceForm.addComponent(new EmptySpace());
	    invoiceForm.addComponent(new Label("Rental"));
	    Double totalRental = new TotalRentalCalculator().calculateRental(reservation.getDailyRental());
	    baseTotal += totalRental;
	    invoiceForm.addComponent(new Label(String.format("$%8.2f", totalRental)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
	    UserRetriever userRetriever = new UserRetriever();
	    customerId = reservation.getUserId();
	    //User customer = userRetriever.retrieveById(customerId);
	    //TierBenefits tierBenefits = new TierBenefits(customer.getLifetimePoints());
	    Double insurance = Double.parseDouble(reservation.getInsurance());
	    baseTotal += insurance;
	    invoiceForm.addComponent(new Label("Insurance"));
	    invoiceForm.addComponent(new Label(String.format("$%8.2f", insurance)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
	    invoiceForm.addComponent(new EmptySpace());
	    invoiceForm.addComponent(new EmptySpace());
	    panel.addComponent(invoiceForm);
	    Panel penaltyForm = new Panel();
	    penaltyForm.setLayoutManager(new GridLayout(1));
	    Table<String> penaltyTable = new Table<String>("Penalties", "");
	    String addPenalty = "+ Add Penalty";
	    penaltyTable.getTableModel().addRow(addPenalty, "");
	    penaltyTable.setSelectAction(() -> {
    		List<String> row = penaltyTable.getTableModel().getRow(penaltyTable.getSelectedRow());
    		if (row.get(0).equals(addPenalty)) {
    			new PenaltyCreator().showPenaltyCreator(penaltyTable);
    		} else {
    			
    		}
    	});
	    penaltyForm.addComponent(penaltyTable);
	    panel.addComponent(penaltyForm);
	    Button generateInvoiceBtn = new Button("Generate Invoice", () -> {
	    	generateInvoice();
	    	new MessageDialogBuilder()
    		.setTitle("")
    		.setText("Invoice created successfully.")
    		.build()
    		.showDialog(gui);
	    	menuWindow.close();
	    	new ReservationSearch().showReservationSearchForm();
	    });
	    panel.addComponent(new EmptySpace());
	    panel.addComponent(generateInvoiceBtn);
	    menuWindow.setComponent(panel);
	    gui.addWindowAndWait(menuWindow);
	}
	
	public void generateInvoice() {
		PenaltyRetriever penaltyRetriever = new PenaltyRetriever();
	    Integer lastPenaltyId = Integer.parseInt(PenaltyRetriever.currentLastRowId);
	    Double totalPenalties = 0.0;
		List<String> penaltyIds = new ArrayList<>();
    	Integer offset = lastPenaltyId + 1;
    	PenaltyWriter penaltyWriter = new PenaltyWriter();
    	for (Penalty penalty: penalties) {
    		penalty.setId(offset.toString());
    		penalty.setReservationId(reservationId);
    		penaltyIds.add(offset.toString());
    		totalPenalties += Double.parseDouble(penalty.getAmount());
    		try {
				penaltyWriter.writeToCsv(penalty);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		offset += 1;
    	}
    	InvoiceRetriever invoiceRetriever = new InvoiceRetriever();
    	Integer invoiceId = Integer.parseInt(InvoiceRetriever.currentLastRowId) + 1;
    	Double subtotal = baseTotal + totalPenalties;
    	Invoice invoice = new Invoice(invoiceId.toString(), "Pending", customerId, reservationId, String.join(",", penaltyIds), totalPenalties.toString(), subtotal.toString(), LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
    	try {
    		new InvoiceWriter().writeToCsv(invoice);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
}
