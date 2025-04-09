package org.example.CarMgmt.Billing.Payments;
import java.util.Collections;
import java.util.HashMap;

import org.example.CarMgmt.Beans.Penalty;
import org.example.CarMgmt.Beans.Reservation;

public class ReservationRetriever extends Retriever<Reservation> {
	public static HashMap<String, Reservation> reservations;
	public static String currentLastRowId = "0";

	public ReservationRetriever() {
		super(Reservation.class);
		reservations = (HashMap<String, Reservation>) init("databases/reservations.csv");
		if (reservations.size() > 0) {
			currentLastRowId = Collections.max(reservations.keySet());
		}
	}
}
