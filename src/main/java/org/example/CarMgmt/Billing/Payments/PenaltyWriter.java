package org.example.CarMgmt.Billing.Payments;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.example.CarMgmt.Beans.Reservation;
import org.example.CarMgmt.Constants;
import org.example.CarMgmt.Beans.Penalty;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.example.CarMgmt.Exceptions.RowNotFoundException;

public class PenaltyWriter {
	public void writeToCsv(Penalty bean) throws Exception {
		Constants constants = new Constants();
		String filePath = constants.getCsvBasePath() + "databases/penalties.csv";
		File file = new File(filePath);
		boolean fileExists = file.exists();
        try (java.io.Writer writer = new FileWriter(filePath, true)) {
        	StatefulBeanToCsv<Penalty> beanToCsv = new StatefulBeanToCsvBuilder<Penalty>(writer)
                    .withApplyQuotesToAll(false)
                    .withOrderedResults(true)
                    .withQuotechar('\"')
                    .withSeparator(',')
                    .build();
        	
        	if (fileExists && file.length() > 0) {
        	    writer.write(System.lineSeparator());
        	}

            beanToCsv.write(Collections.singletonList(bean));
        }
    }

	/**
	 * Retrieves Reservation records from the CSV file. Stores them in a static map for quick lookup.
	 */
	public static class ReservationRetriever {
		public static HashMap<String, Reservation> reservations = new HashMap<>();
		public static String currentLastRowId = "0";
		private static final String RESERVATION_CSV_PATH = "Reservation.csv";

		public ReservationRetriever() {
			reservations.clear();
			try (Reader reader = new FileReader(RESERVATION_CSV_PATH)) {
				// Use OpenCSV to map CSV rows to Reservation beans
				CsvToBean<Reservation> csvToBean = new CsvToBeanBuilder<Reservation>(reader)
						.withType(Reservation.class)
						.withIgnoreLeadingWhiteSpace(true)
						.build();
				List<Reservation> resList = csvToBean.parse();
				// Populate the static map and track last ID
				for (Reservation res : resList) {
					reservations.put(res.getId(), res);
					currentLastRowId = res.getId();  // last read ID (assuming CSV is in ascending order by ID)
				}
			} catch (Exception e) {
				// If file not found or parse error, handle gracefully (could be first run with no data)
				currentLastRowId = "0";
			}
		}

		/**
		 * Retrieve a Reservation by its ID.
		 * @throws RowNotFoundException if not found.
		 */
		public Reservation retrieveById(String reservationId) throws RowNotFoundException {
			Reservation res = reservations.get(reservationId);
			if (res == null) {
				throw new RowNotFoundException("Reservation ID " + reservationId + " not found.");
			}
			return res;
		}
	}
}
