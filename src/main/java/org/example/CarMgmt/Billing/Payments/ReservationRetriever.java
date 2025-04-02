package org.example.CarMgmt.Billing.Payments;

import java.util.HashMap;

import org.example.CarMgmt.Beans.CsvBeans;
import org.example.CarMgmt.Beans.Reservation;

public class ReservationRetriever extends Retriever<Reservation> {
	HashMap<String, CsvBeans> hashmap;
	
	public ReservationRetriever() {
		super(Reservation.class);
		init("databases/reservations.csv");
	}
}
