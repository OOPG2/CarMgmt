package app;

import beans.Reservation;

import java.util.Collections;
import java.util.HashMap;

public class ReservationRetriever extends Retriever<Reservation>{
    public static HashMap<String, Reservation> reservations;
    public static String currentLastRowId = "0";
    public ReservationRetriever() {
        super(Reservation.class);
        reservations = (HashMap<String, Reservation>) init("databases/reservations.csv");
        if (reservations.size() > 0) {
            currentLastRowId = Collections.max(reservations.keySet());
        }
    }

    public HashMap<String, Reservation> getReservations(){
        return reservations;
    }

    public String getCurrentLastRowId() {
        return currentLastRowId;
    }
}
