package org.example.crms;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages users, vehicles, and reservations data, providing operations 
 * to create, modify, cancel reservations, and update their status.
 * Data is persisted in CSV files (users.csv, vehicles.csv, reservations.csv) via OpenCSV.
 */
public class ReservationManager {
    private static final String USERS_CSV = "src/main/java/org/example/crms/users.csv";
    private static final String VEHICLES_CSV = "src/main/java/org/example/crms/vehicles.csv";
    private static final String RESERVATIONS_CSV = "src/main/java/org/example/crms/reservations.csv";


    private List<User> users;
    private List<Vehicle> vehicles;
    private List<Reservation> reservations;
    // Maps for quick lookup by ID
    private Map<Integer, User> userMap;
    private Map<Integer, Vehicle> vehicleMap;
    // Next reservation ID to assign for new reservations
    private int nextReservationId;

    public ReservationManager() {
        // Load data from CSV files at initialization
        loadUsers();
        loadVehicles();
        loadReservations();
    }

    /** Load all users from users CSV into the users list and userMap. */
    private void loadUsers() {
        users = new ArrayList<>();
        userMap = new HashMap<>();
        try (Reader reader = new FileReader(USERS_CSV)) {
            HeaderColumnNameMappingStrategy<User> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(User.class);
            CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            users = csvToBean.parse();
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
            // If file not found or error, leave users list empty
        }
        for (User u : users) {
            userMap.put(u.getId(), u);
        }
    }

    /** Load all vehicles from vehicles CSV into the vehicles list and vehicleMap. */
    private void loadVehicles() {
        vehicles = new ArrayList<>();
        vehicleMap = new HashMap<>();
        try (Reader reader = new FileReader(VEHICLES_CSV)) {
            HeaderColumnNameMappingStrategy<Vehicle> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Vehicle.class);
            CsvToBean<Vehicle> csvToBean = new CsvToBeanBuilder<Vehicle>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            vehicles = csvToBean.parse();
        } catch (IOException e) {
            System.err.println("Error loading vehicles: " + e.getMessage());
        }
        for (Vehicle v : vehicles) {
            vehicleMap.put(v.getId(), v);
        }
    }

    /** Load all reservations from reservations CSV into the reservations list and set nextReservationId. */
    private void loadReservations() {
        reservations = new ArrayList<>();
        nextReservationId = 1;
        try (Reader reader = new FileReader(RESERVATIONS_CSV)) {
            HeaderColumnNameMappingStrategy<Reservation> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Reservation.class);
            CsvToBean<Reservation> csvToBean = new CsvToBeanBuilder<Reservation>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            reservations = csvToBean.parse();
        } catch (IOException e) {
            System.err.println("Error loading reservations: " + e.getMessage());
        }
        // Determine nextReservationId (max existing id + 1)
        for (Reservation r : reservations) {
            if (r.getId() >= nextReservationId) {
                nextReservationId = r.getId() + 1;
            }
        }
    }

    /** Save the current reservations list back to the reservations CSV file. */
    private void saveReservations() {
        try (Writer writer = new FileWriter(RESERVATIONS_CSV)) {
            // Write CSV header
            writer.write("id,userId,vehicleId,start,end,status\n");
            StatefulBeanToCsv<Reservation> beanToCsv = new StatefulBeanToCsvBuilder<Reservation>(writer)
                    .withApplyQuotesToAll(false)  // no need to quote all fields
                    .build();
            beanToCsv.write(reservations);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            System.err.println("Error saving reservations: " + e.getMessage());
        }
    }

    /** Save the users list to the users CSV file (if any changes were made to users). */
    private void saveUsers() {
        try (Writer writer = new FileWriter(USERS_CSV)) {
            writer.write("id,username,password,role\n");
            StatefulBeanToCsv<User> beanToCsv = new StatefulBeanToCsvBuilder<User>(writer)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(users);
        } catch (Exception e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    /** Save the vehicles list to the vehicles CSV file (if any changes were made to vehicles). */
    private void saveVehicles() {
        try (Writer writer = new FileWriter(VEHICLES_CSV)) {
            writer.write("id,make,model,year,type\n");
            StatefulBeanToCsv<Vehicle> beanToCsv = new StatefulBeanToCsvBuilder<Vehicle>(writer)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(vehicles);
        } catch (Exception e) {
            System.err.println("Error saving vehicles: " + e.getMessage());
        }
    }

    /**
     * Authenticate a user by username and password.
     * @return the User object if credentials match, or null if not found or incorrect.
     */
    public User authenticate(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Create a new reservation for the given user and vehicle, if the vehicle is available.
     * Assigns a new reservation ID and sets status to "Reserved".
     * @return the newly created Reservation object.
     * @throws IllegalArgumentException if times are invalid or vehicle is not available.
     */
    public Reservation createReservation(User user, int vehicleId, LocalDateTime start, LocalDateTime end) {
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }
        if (!vehicleMap.containsKey(vehicleId)) {
            throw new IllegalArgumentException("Vehicle ID " + vehicleId + " does not exist.");
        }
        // Check availability for the requested period
        if (!VehicleAvailabilityChecker.isVehicleAvailable(vehicleId, start, end, reservations, null)) {
            throw new IllegalArgumentException("Vehicle is not available in the selected time range.");
        }
        // Create the reservation and add to list
        Reservation newRes = new Reservation(nextReservationId++, user.getId(), vehicleId, start, end, "Reserved");
        reservations.add(newRes);
        saveReservations();
        return newRes;
    }

    /**
     * Modify an existing reservation's vehicle and/or time, if allowed.
     * Only reservations in "Reserved" status (not yet picked up) can be modified.
     * @param user The user attempting the modification (to check permissions).
     * @param reservationId The ID of the reservation to modify.
     * @param newVehicleId (Optional) New vehicle ID, if changing vehicle.
     * @param newStart New start date-time.
     * @param newEnd New end date-time.
     * @throws IllegalArgumentException if reservation not found, user not authorized, or schedule conflicts occur.
     */
    public void modifyReservation(User user, int reservationId, Integer newVehicleId, LocalDateTime newStart, LocalDateTime newEnd) {
        Reservation res = getReservationById(reservationId);
        if (res == null) {
            throw new IllegalArgumentException("Reservation ID not found.");
        }
        // Customers can only modify their own reservations; staff can modify any
        if (!user.getRole().equalsIgnoreCase("Staff") && res.getUserId() != user.getId()) {
            throw new IllegalArgumentException("You can only modify your own reservations.");
        }
        if (!res.getStatus().equalsIgnoreCase("Reserved")) {
            throw new IllegalArgumentException("Reservation cannot be modified (already picked up, returned, or cancelled).");
        }
        if (!newEnd.isAfter(newStart)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }
        // Determine the vehicle ID to use (if changing vehicle or keeping the same)
        int effectiveVehicleId = (newVehicleId != null ? newVehicleId : res.getVehicleId());
        if (!vehicleMap.containsKey(effectiveVehicleId)) {
            throw new IllegalArgumentException("Vehicle ID " + effectiveVehicleId + " does not exist.");
        }
        // Check availability for the new schedule (exclude this reservation's current record from conflict checking)
        if (!VehicleAvailabilityChecker.isVehicleAvailable(effectiveVehicleId, newStart, newEnd, reservations, res.getId())) {
            throw new IllegalArgumentException("Vehicle is not available in the selected time range.");
        }
        // Apply changes
        res.setVehicleId(effectiveVehicleId);
        res.setStart(Reservation.DATETIME_FORMAT.format(newStart));
        res.setEnd(Reservation.DATETIME_FORMAT.format(newEnd));
        saveReservations();
    }

    /**
     * Cancel a reservation (mark status as "Cancelled") if it has not been picked up yet.
     * @throws IllegalArgumentException if not found, user not authorized, or reservation already in progress/completed.
     */
    public void cancelReservation(User user, int reservationId) {
        Reservation res = getReservationById(reservationId);
        if (res == null) {
            throw new IllegalArgumentException("Reservation ID not found.");
        }
        if (!user.getRole().equalsIgnoreCase("Staff") && res.getUserId() != user.getId()) {
            throw new IllegalArgumentException("You can only cancel your own reservations.");
        }
        if (!res.getStatus().equalsIgnoreCase("Reserved")) {
            throw new IllegalArgumentException("Reservation cannot be cancelled (already picked up or completed).");
        }
        res.setStatus("Cancelled");
        saveReservations();
    }

    /**
     * Mark a reservation as picked up (status = "PickedUp").
     * @throws IllegalArgumentException if reservation not found or not in Reserved status.
     */
    public void confirmPickup(int reservationId) {
        Reservation res = getReservationById(reservationId);
        if (res == null) {
            throw new IllegalArgumentException("Reservation ID not found.");
        }
        if (!res.getStatus().equalsIgnoreCase("Reserved")) {
            throw new IllegalArgumentException("Only a reservation that is Reserved can be picked up.");
        }
        res.setStatus("PickedUp");
        saveReservations();
    }

    /**
     * Mark a reservation as returned (status = "Returned").
     * @throws IllegalArgumentException if reservation not found or not in PickedUp status.
     */
    public void confirmReturn(int reservationId) {
        Reservation res = getReservationById(reservationId);
        if (res == null) {
            throw new IllegalArgumentException("Reservation ID not found.");
        }
        if (!res.getStatus().equalsIgnoreCase("PickedUp")) {
            throw new IllegalArgumentException("Only a reservation that is PickedUp can be returned.");
        }
        res.setStatus("Returned");
        saveReservations();
    }

    /** Retrieve a reservation by its ID (or null if not found). */
    public Reservation getReservationById(int reservationId) {
        for (Reservation r : reservations) {
            if (r.getId() == reservationId) {
                return r;
            }
        }
        return null;
    }

    /** Retrieve a user by their ID (or null if not found). */
    public User getUserById(int userId) {
        return userMap.get(userId);
    }

    /** Retrieve a vehicle by its ID (or null if not found). */
    public Vehicle getVehicleById(int vehicleId) {
        return vehicleMap.get(vehicleId);
    }

    /** Get all reservations made by a specific user. Staff can use this to get a customer's reservations. */
    public List<Reservation> getReservationsForUser(int userId) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getUserId() == userId) {
                result.add(r);
            }
        }
        return result;
    }

    /** Get a list of all reservations (for staff usage). */
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }
}
