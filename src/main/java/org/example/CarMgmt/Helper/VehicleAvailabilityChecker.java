package org.example.CarMgmt.Helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.example.CarMgmt.Beans.Reservation;
import org.example.CarMgmt.Billing.Payments.PenaltyWriter;

/**
 * Utility class to check if a vehicle is available for a given date range.
 */
public class VehicleAvailabilityChecker {

    /**
     * Check availability of a vehicle for the given date range.
     * This method considers all existing reservations (excluding a specific reservation if provided).
     * @param vehicleId    the vehicle to check
     * @param startDateStr desired start date (YYYY-MM-DD format)
     * @param endDateStr   desired end date (YYYY-MM-DD format)
     * @param excludeReservationId (optional) an existing reservation ID to ignore (for modifications)
     * @return true if no conflicting reservation exists for that vehicle in the given range.
     */
    public static boolean isAvailable(String vehicleId, String startDateStr, String endDateStr, String excludeReservationId) {
        // Parse dates
        LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        // Load all reservations
        PenaltyWriter.ReservationRetriever retriever = new PenaltyWriter.ReservationRetriever();
        List<Reservation> allReservations = List.copyOf(PenaltyWriter.ReservationRetriever.reservations.values());
        for (Reservation res : allReservations) {
            if (excludeReservationId != null && res.getId().equals(excludeReservationId)) {
                continue; // skip the reservation we're modifying (if any)
            }
            if (!res.getStatus().equalsIgnoreCase("cancelled") && !res.getStatus().equalsIgnoreCase("completed")) {
                if (res.getVehicleId().equals(vehicleId)) {
                    // Parse this reservation's dates
                    LocalDate existingStart = LocalDate.parse(res.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                    LocalDate existingEnd = LocalDate.parse(res.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                    // Check for overlap: if new start <= existing end AND new end >= existing start
                    if (!startDate.isAfter(existingEnd) && !endDate.isBefore(existingStart)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Overloaded convenience method (no reservation to exclude).
     */
    public static boolean isAvailable(String vehicleId, String startDateStr, String endDateStr) {
        return isAvailable(vehicleId, startDateStr, endDateStr, null);
    }
}
