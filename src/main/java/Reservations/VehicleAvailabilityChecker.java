package Reservations;


import beans.Reservation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Utility class to check a vehicle's availability for a given time range.
 * Ensures no existing reservation for the same vehicle overlaps the desired period.
 */
public class VehicleAvailabilityChecker {
    /**
     * Checks if a vehicle is available from newStart to newEnd.
     *
     * @param vehicleId    The ID of the vehicle to check.
     * @param newStart     Desired start time (inclusive).
     * @param newEnd       Desired end time (exclusive).
     * @param reservations List of all existing reservations to check against.
     * @param excludeResId If not null, this reservation ID will be excluded from conflict checking
     *                     (useful when modifying an existing reservation).
     * @return true if the vehicle is available (no overlapping reservation in that period), false otherwise.
     */
    public static boolean isVehicleAvailable(int vehicleId, LocalDateTime newStart, LocalDateTime newEnd,
                                             List<Reservation> reservations, Integer excludeResId) {
        for (Reservation res : reservations) {
            if (excludeResId != null && res.getReservationID() == excludeResId) {
                continue; // Skip the reservation being modified (itself)
            }
            if (res.getVehicleId() != vehicleId) {
                continue; // Different vehicle, not a conflict
            }
            String status = res.getStatus();
            // Only consider reservations that are active (not canceled or completed)
            if (status.equalsIgnoreCase("Cancelled") ||
                    status.equalsIgnoreCase("Returned") ||
                    status.equalsIgnoreCase("Completed")) {
                continue;
            }
            // Parse existing reservation times
            LocalDateTime existingStart = res.getStartDateTime();
            LocalDateTime existingEnd = res.getEndDateTime();
            // Check for overlap: newStart < existingEnd and newEnd > existingStart indicates time overlap
            if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
                return false;  // Time conflict found
            }
        }
        return true;
    }
}
