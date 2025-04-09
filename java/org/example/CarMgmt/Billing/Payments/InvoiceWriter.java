package org.example.CarMgmt.Billing.Payments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.example.CarMgmt.Beans.Reservation;
import org.example.CarMgmt.Constants;
import org.example.CarMgmt.Beans.Invoice;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class InvoiceWriter {
	public void writeToCsv(Invoice bean) throws Exception {
		Constants constants = new Constants();
		String filePath = constants.getCsvBasePath() + "databases/invoices.csv";
		File file = new File(filePath);
		boolean fileExists = file.exists();
        try (java.io.Writer writer = new FileWriter(filePath, true)) {
        	StatefulBeanToCsv<Invoice> beanToCsv = new StatefulBeanToCsvBuilder<Invoice>(writer)
                    .withApplyQuotesToAll(false)
                    .withOrderedResults(true)
                    .withQuotechar('\"')
                    .withSeparator(',')
                    .build();
        	/*
        	if (fileExists && file.length() > 0) {
        	    writer.write(System.lineSeparator());
        	}
        	*/

            beanToCsv.write(Collections.singletonList(bean));
        }
    }

    /**
     * Handles writing Reservation records to the CSV file.
     */
    public static class ReservationWriter {
        private static final String RESERVATION_CSV_PATH = "Reservation.csv";  // Path to reservations CSV file

        /**
         * Appends a single Reservation record to the CSV file.
         */
        public void writeToCsv(Reservation reservation) throws IOException {
            File file = new File(RESERVATION_CSV_PATH);
            boolean fileExists = file.exists();
            try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {
                // If file is new, you could write header here (not required if not using CsvBindByName)
                String[] entry = {
                        reservation.getId(),
                        reservation.getUserId(),
                        reservation.getVehicleId(),
                        reservation.getStatus(),
                        reservation.getStartDate(),
                        reservation.getEndDate(),
                        reservation.getDailyRental(),
                        reservation.getInsurance(),
                        reservation.getCustomerNotes()
                };
                writer.writeNext(entry);
            }
        }

        /**
         * Overwrites the entire Reservation CSV with the given collection of reservations.
         * This is used for updates (modify, cancel, status change) to rewrite all records.
         */
        public void writeAllToCsv(Collection<Reservation> reservations) throws IOException {
            try (CSVWriter writer = new CSVWriter(new FileWriter(RESERVATION_CSV_PATH, false))) {
                // Optionally, write header row if needed
                for (Reservation res : reservations) {
                    String[] entry = {
                            res.getId(),
                            res.getUserId(),
                            res.getVehicleId(),
                            res.getStatus(),
                            res.getStartDate(),
                            res.getEndDate(),
                            res.getDailyRental(),
                            res.getInsurance(),
                            res.getCustomerNotes()
                    };
                    writer.writeNext(entry);
                }
            }
        }
    }
}
