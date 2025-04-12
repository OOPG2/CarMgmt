package Reservations;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import beans.CsvBeans;
import java.io.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import constants.Constants;
import objects.*;
public class ReservationManager {
    private static final String USERS_CSV = "src/main/resources/databases/users.csv";
    private static final String VEHICLES_CSV = "src/main/resources/databases/vehicles.csv";
    private static final String RESERVATIONS_CSV = "src/main/resources/databases/reservations.csv";

    private List<User> users;
    private List<Vehicle> vehicles;
    private List<Reservation> reservations;

    private Map<String, User> userMap;
    private Map<Integer, Vehicle> vehicleMap;
    private int nextReservationId;

    public ReservationManager() {
        loadUsers();
        loadVehicles();
        loadReservations();
    }

    private void loadUsers() {
        users = new ArrayList<>();
        userMap = new HashMap<>();

        try (Reader reader = new FileReader(USERS_CSV)) {
            CsvToBean<CsvBeans> csvToBean = new CsvToBeanBuilder<CsvBeans>(reader)
                    .withType(CsvBeans.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<CsvBeans> beans = csvToBean.parse();

            for (CsvBeans bean : beans) {
                if (!"customer".equalsIgnoreCase(bean.getRole())) {
                    continue; // skip non-customers
                }

                Customer customer = new Customer(
                        bean.getUserId(),
                        bean.getPassword(),
                        bean.getRole(),
                        bean.getName(),
                        bean.getEmail(),
                        bean.getPhone(),
                        bean.getLifetimePoints(),
                        bean.getLoyaltyPoints(),
                        bean.isBanned()
                );

                users.add(customer);
                userMap.put(customer.getUserId(), customer);
            }

        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
    }
    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }


    private void loadVehicles() {
        vehicles = new ArrayList<>();
        vehicleMap = new HashMap<>();
        try (Reader reader = new FileReader(VEHICLES_CSV)) {
            com.opencsv.bean.ColumnPositionMappingStrategy<Vehicle> strategy = new com.opencsv.bean.ColumnPositionMappingStrategy<>();
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
        for (Reservation r : reservations) {
            if (r.getId() >= nextReservationId) {
                nextReservationId = r.getId() + 1;
            }
        }
    }

    public Reservation createReservation(User user, int vehicleId, LocalDateTime start, LocalDateTime end) {
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }

        Vehicle vehicle = vehicleMap.get(vehicleId);
        if (vehicle == null || !"Available".equalsIgnoreCase(vehicle.getStatus())) {
            throw new IllegalArgumentException("Vehicle not available.");
        }

        Long daysDiff = ChronoUnit.DAYS.between(start, end);
        List<String> dailyRentalList = new ArrayList<>();
        for (int i = 0; i < daysDiff; i++) {
            dailyRentalList.add(String.format("%.2f", vehicle.getRentalCost()));
        }
        String dailyRental = String.join(",", dailyRentalList);
        Reservation newRes = new Reservation(nextReservationId++, user.getUserId(), vehicleId, start, end, "Reserved", dailyRental, String.format("%.2f", Constants.getDailyInsurance() * daysDiff));
        reservations.add(newRes);
        vehicle.setStatus("Not Available");

        saveReservations();
        saveVehicles();

        return newRes;
    }

    public void modifyReservation(User user, int reservationId, Integer newVehicleId, LocalDateTime newStart, LocalDateTime newEnd) {
        Reservation res = getReservationById(reservationId);
        if (res == null || !"Reserved".equalsIgnoreCase(res.getStatus())) {
            throw new IllegalArgumentException("Cannot modify this reservation.");
        }
        if (!user.getUserId().equals(res.getUserId()) && !"Staff".equalsIgnoreCase(user.getRole())) {
            throw new IllegalArgumentException("Unauthorized to modify this reservation.");
        }

        int effectiveVehicleId = newVehicleId != null ? newVehicleId : res.getVehicleId();
        if (!vehicleMap.containsKey(effectiveVehicleId)) {
            throw new IllegalArgumentException("Vehicle does not exist.");
        }

        res.setVehicleId(effectiveVehicleId);
        res.setStart(Reservation.DATETIME_FORMAT.format(newStart));
        res.setEnd(Reservation.DATETIME_FORMAT.format(newEnd));
        saveReservations();
    }

    public void cancelReservation(User user, int reservationId) {
        Reservation res = getReservationById(reservationId);
        if (res == null || !"Reserved".equalsIgnoreCase(res.getStatus())) {
            throw new IllegalArgumentException("Cannot cancel this reservation.");
        }
        if (!user.getUserId().equals(res.getUserId()) && !"Staff".equalsIgnoreCase(user.getRole())) {
            throw new IllegalArgumentException("Unauthorized to cancel this reservation.");
        }

        res.setStatus("Cancelled");
        saveReservations();
    }

    public void confirmPickup(int reservationId) {
        Reservation res = getReservationById(reservationId);
        if (res != null && "Reserved".equalsIgnoreCase(res.getStatus())) {
            res.setStatus("PickedUp");
            saveReservations();
        }
    }

    public void confirmReturn(int reservationId) {
        Reservation res = getReservationById(reservationId);
        if (res != null && "PickedUp".equalsIgnoreCase(res.getStatus())) {
            res.setStatus("Returned");
            saveReservations();
        }
    }

    public Reservation getReservationById(int reservationId) {
        for (Reservation r : reservations) {
            if (r.getId() == reservationId) return r;
        }
        return null;
    }

    public User getUserById(String userId) {
        return userMap.get(userId);
    }

    public Vehicle getVehicleById(int vehicleId) {
        return vehicleMap.get(vehicleId);
    }

    public List<Reservation> getReservationsForUser(String userId) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getUserId().equals(userId)) result.add(r);
        }
        return result;
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }

    private void saveReservations() {
        try (Writer writer = new FileWriter(RESERVATIONS_CSV)) {
            StatefulBeanToCsv<Reservation> beanToCsv = new StatefulBeanToCsvBuilder<Reservation>(writer)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(reservations);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            System.err.println("Error saving reservations: " + e.getMessage());
        }
    }
    public User authenticate(String userId, String password) {
        for (User u : users) {
            if (u.getUserId().equalsIgnoreCase(userId) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }
    void saveVehicles() {
        try (Writer writer = new FileWriter(VEHICLES_CSV)) {
            StatefulBeanToCsv<Vehicle> beanToCsv = new StatefulBeanToCsvBuilder<Vehicle>(writer)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(vehicles);
        } catch (Exception e) {
            System.err.println("Error saving vehicles: " + e.getMessage());
        }
    }
}
