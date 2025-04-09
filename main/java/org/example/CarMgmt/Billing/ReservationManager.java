package org.example.CarMgmt.Billing;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.example.CarMgmt.Beans.Reservation;
import org.example.CarMgmt.Billing.Payments.InvoiceWriter;
import org.example.CarMgmt.Beans.Penalty;
import org.example.CarMgmt.Billing.Payments.PenaltyRetriever;
import org.example.CarMgmt.Billing.Payments.PenaltyWriter;
import org.example.CarMgmt.Helper.VehicleAvailabilityChecker;

/**
 * Controller/service class for managing Reservations: creation, modification,
 * cancellation, and status updates. Uses CSV persistence via ReservationWriter/ReservationRetriever.
 */
public class ReservationManager {

    /**
     * Create a new reservation for a user. Generates a new ID, checks vehicle availability,
     * and saves to CSV. Returns the created Reservation or throws exception on failure.
     */
    public static Reservation createReservation(String userId, String vehicleId,
                                                String startDate, String endDate,
                                                String dailyRental, String insurance,
                                                String customerNotes) throws Exception {
        // Load current reservations and determine next ID
        PenaltyWriter.ReservationRetriever retriever = new PenaltyWriter.ReservationRetriever();
        // Check vehicle availability for the requested date range
        if (!VehicleAvailabilityChecker.isAvailable(vehicleId, startDate, endDate)) {
            throw new Exception("Vehicle not available for the selected date range.");
        }
        // Generate new reservation ID (last ID + 1)
        int newId = 1;
        try {
            newId = Integer.parseInt(PenaltyWriter.ReservationRetriever.currentLastRowId) + 1;
        } catch (NumberFormatException e) {
            // If no existing ID (e.g., first entry), start from 1
        }
        Reservation reservation = new Reservation();
        reservation.setId(String.valueOf(newId));
        reservation.setUserId(userId);
        reservation.setVehicleId(vehicleId);
        reservation.setStatus("reserved");
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setDailyRental(dailyRental);
        reservation.setInsurance(insurance);
        reservation.setCustomerNotes(customerNotes);
        // Write new reservation to CSV (append)
        new InvoiceWriter.ReservationWriter().writeToCsv(reservation);
        // Update vehicle status to reserved (placeholder call to VehicleManager)
        // VehicleManager.updateVehicleStatus(vehicleId, "Reserved");
        return reservation;
    }

    /**
     * Modify an existing reservation's date range (and notes/insurance if needed) if it has not been collected.
     * Updates the reservation record in CSV. Returns the updated Reservation.
     */
    public static Reservation modifyReservation(String reservationId,
                                                String newStartDate, String newEndDate,
                                                String newInsurance, String newNotes) throws Exception {
        PenaltyWriter.ReservationRetriever retriever = new PenaltyWriter.ReservationRetriever();
        Reservation res = PenaltyWriter.ReservationRetriever.reservations.get(reservationId);
        if (res == null) {
            throw new Exception("Reservation not found.");
        }
        // Only allow modification if not yet collected
        if (res.getStatus().equalsIgnoreCase("collected") || res.getStatus().equalsIgnoreCase("completed")) {
            throw new Exception("Cannot modify a reservation that has been collected or completed.");
        }
        // Check availability for the vehicle on new dates, excluding this reservation's current slot
        if (!VehicleAvailabilityChecker.isAvailable(res.getVehicleId(), newStartDate, newEndDate, reservationId)) {
            throw new Exception("Vehicle not available for the new date range.");
        }
        // Update the reservation fields
        res.setStartDate(newStartDate);
        res.setEndDate(newEndDate);
        if (newInsurance != null) {
            res.setInsurance(newInsurance);
        }
        if (newNotes != null) {
            res.setCustomerNotes(newNotes);
        }
        // Persist all reservations to CSV (overwrite file with updated data)
        new InvoiceWriter.ReservationWriter().writeAllToCsv(PenaltyWriter.ReservationRetriever.reservations.values());
        return res;
    }

    /**
     * Cancel an existing reservation if it has not been collected yet.
     * If cancelled on the pickup day, a cancellation penalty is recorded.
     */
    public static void cancelReservation(String reservationId) throws Exception {
        PenaltyWriter.ReservationRetriever retriever = new PenaltyWriter.ReservationRetriever();
        Reservation res = PenaltyWriter.ReservationRetriever.reservations.get(reservationId);
        if (res == null) {
            throw new Exception("Reservation not found.");
        }
        // Only allow cancellation if not already completed/collected
        if (res.getStatus().equalsIgnoreCase("collected") || res.getStatus().equalsIgnoreCase("completed")) {
            throw new Exception("Cannot cancel a reservation that has been collected or completed.");
        }
        // Determine if cancellation occurs on pickup day
        LocalDate today = LocalDate.now();
        LocalDate startDay = LocalDate.parse(res.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        boolean sameDayCancel = today.equals(startDay);
        // Update reservation status to cancelled
        res.setStatus("cancelled");
        // Persist change to CSV
        new InvoiceWriter.ReservationWriter().writeAllToCsv(PenaltyWriter.ReservationRetriever.reservations.values());
        // If cancelled on pickup day, enforce penalty (e.g., charge one day rental as fee)
        if (sameDayCancel) {
            // Create a penalty record for late cancellation
            Penalty cancellationPenalty = new Penalty();
            // Generate new penalty ID
            PenaltyRetriever penaltyRetriever = new PenaltyRetriever();
            int newPenaltyId = 1;
            try {
                newPenaltyId = Integer.parseInt(PenaltyRetriever.currentLastRowId) + 1;
            } catch (NumberFormatException e) { }
            cancellationPenalty.setId(String.valueOf(newPenaltyId));
            cancellationPenalty.setReservationId(reservationId);
            cancellationPenalty.setDescription("Late Cancellation");
            // Use one day rental amount as penalty fee
            try {
                double fee = Double.parseDouble(res.getDailyRental());
                cancellationPenalty.setAmount(String.format("%.2f", fee));
            } catch (NumberFormatException e) {
                cancellationPenalty.setAmount("0.00");
            }
            // Write penalty to CSV
            new PenaltyWriter().writeToCsv(cancellationPenalty);
        }
        // Free up vehicle (placeholder call to VehicleManager)
        // VehicleManager.updateVehicleStatus(res.getVehicleId(), "Available");
    }

    /**
     * Mark a reserved reservation as collected (pickup confirmed).
     * This should be done on the pickup date by staff.
     */
    public static void confirmPickup(String reservationId) throws Exception {
        PenaltyWriter.ReservationRetriever retriever = new PenaltyWriter.ReservationRetriever();
        Reservation res = PenaltyWriter.ReservationRetriever.reservations.get(reservationId);
        if (res == null) {
            throw new Exception("Reservation not found.");
        }
        if (!res.getStatus().equalsIgnoreCase("reserved") && !res.getStatus().equalsIgnoreCase("confirmed")) {
            throw new Exception("Only a reserved reservation can be picked up.");
        }
        // Update status to collected (vehicle picked up by customer)
        res.setStatus("collected");
        new InvoiceWriter.ReservationWriter().writeAllToCsv(PenaltyWriter.ReservationRetriever.reservations.values());
        // Mark vehicle as now in use (placeholder)
        // VehicleManager.updateVehicleStatus(res.getVehicleId(), "Unavailable");
    }

    /**
     * Mark a collected reservation as completed (vehicle returned).
     * Staff should call this upon vehicle return.
     */
    public static void confirmReturn(String reservationId) throws Exception {
        PenaltyWriter.ReservationRetriever retriever = new PenaltyWriter.ReservationRetriever();
        Reservation res = PenaltyWriter.ReservationRetriever.reservations.get(reservationId);
        if (res == null) {
            throw new Exception("Reservation not found.");
        }
        if (!res.getStatus().equalsIgnoreCase("collected")) {
            throw new Exception("Only a collected (in-progress) reservation can be marked as returned.");
        }
        // Update status to completed (rental finished)
        res.setStatus("completed");
        new InvoiceWriter.ReservationWriter().writeAllToCsv(PenaltyWriter.ReservationRetriever.reservations.values());
        // Mark vehicle as available again (placeholder)
        // VehicleManager.updateVehicleStatus(res.getVehicleId(), "Available");
    }
}
