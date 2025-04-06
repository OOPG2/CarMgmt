package org.example.CarMgmt.Billing.Payments;
import java.util.HashMap;

import org.example.CarMgmt.Beans.Penalty;
import org.example.CarMgmt.Beans.Reservation;

public class ReservationRetriever extends Retriever<Reservation> {
	public static HashMap<String, Reservation> reservations;

	public ReservationRetriever() {
		super(Reservation.class);
		reservations = (HashMap<String, Reservation>) init("databases/reservations.csv");
	}
}
