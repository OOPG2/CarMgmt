package billing.invoice;

import app.App;
import beans.Invoice;
import beans.Penalty;
import beans.Reservation;
import billing.helper.TotalRentalCalculator;
import billing.penalty.PenaltyRetriever;
import billing.penalty.PenaltyWriter;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.table.Table;
import manager.UserManager;
import menus.LoggedMenu;
import objects.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InvoiceGenerator {
	public List<Penalty> penalties = new ArrayList<>();
	String reservationId;
	String customerId;
	Double baseTotal = 0.0;
	Integer redeemedDollarAmount = 0;
	Integer pointsDeducted = 0;
	Customer customer;
	Integer customerPoints = 0;
	App app = new App();
	UserManager userManager = app.getUserManager();
	public static BasicWindow menuWindow = new BasicWindow(String.format("Generate Invoice"));
	public void showInvoiceGenerator(Reservation reservation) {
		MultiWindowTextGUI gui = App.gui;
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
		Button back = new Button("Back", () -> {
			menuWindow.close();
			LoggedMenu.showLoggedMenu();
		});
		panel.addComponent(back);
		Panel invoiceForm = new Panel();
		invoiceForm.setLayoutManager(new GridLayout(2));
		reservationId = String.valueOf(reservation.getId());
		invoiceForm.addComponent(new Label("Reservation No.:"));
		invoiceForm.addComponent(new Label(reservationId));
		invoiceForm.addComponent(new EmptySpace());
		invoiceForm.addComponent(new EmptySpace());
		invoiceForm.addComponent(new Label("Base Items").addStyle(SGR.UNDERLINE).addStyle(SGR.BOLD));
		invoiceForm.addComponent(new EmptySpace());
		invoiceForm.addComponent(new Label("Rental"));
		Double totalRental = new TotalRentalCalculator().calculateRental(reservation.getDailyRental());
		baseTotal += totalRental;
		invoiceForm.addComponent(new Label(String.format(" $%8.2f", totalRental)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
		customerId = reservation.getUserId();
		customer = (Customer) userManager.getUserByID(customerId);
		customerPoints = customer.getLoyaltyPoints();
		Double insurance = Double.parseDouble(reservation.getInsurance());
		baseTotal += insurance;
		invoiceForm.addComponent(new Label("Insurance"));
		invoiceForm.addComponent(new Label(String.format("$%8.2f", insurance)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
		if (customerPoints >= 100) {
			invoiceForm.addComponent(new Label("Automatic Points Redemption"));
			redeemedDollarAmount = (customerPoints / 100);
			if (redeemedDollarAmount > baseTotal) {
				redeemedDollarAmount = (int) ((baseTotal / 100) * 100);
			}
			pointsDeducted = redeemedDollarAmount * 100;
			invoiceForm.addComponent(new Label(String.format("-$%5d.00", redeemedDollarAmount)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
		}
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
				new PenaltyCreator().showPenaltyCreator(penaltyTable, penalties);
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
		new PenaltyRetriever();
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
		Double subtotal = baseTotal + totalPenalties - redeemedDollarAmount;
		Invoice invoice = new Invoice(invoiceId.toString(), "Pending", customerId, reservationId, String.join(",", penaltyIds), totalPenalties.toString(), redeemedDollarAmount.toString(), baseTotal.toString(), subtotal.toString(), LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
		try {
			new InvoiceWriter().writeToCsv(invoice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (pointsDeducted > 0) {
			customer.setLoyaltyPoints(customerPoints - pointsDeducted);
			userManager.updateUser(customer);
		}
	}
}
