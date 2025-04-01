package org.example.CarMgmt.manager;

import org.example.CarMgmt.objects.User;
import org.example.CarMgmt.objects.Customer;
import org.example.CarMgmt.objects.Staff;
import org.example.CarMgmt.objects.Admin;


import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

public class UserManager {
    private String csvFile = "src/main/java/org/example/CarMgmt/databases/users.csv";
    static User loggedUser = null;

    public void createUser(String username, String password, String type) {
        if (username != null && password != null) {
            String[] userInfo = new String[]{
                    generateUniqueUuid(),
                    username,
                    type,
                    "Active",
                    "Test User",
                    "12345678",
                    "test@email.com",
                    password
            };

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, true))) {
                writer.newLine();
                writer.write(String.join(",", userInfo));
                System.out.println("Data appended to " + csvFile);
            } catch (IOException e) {
                System.err.println("Error writing to file (createUser): " + e.getMessage());
            }
        }
    }

    public Map<String, User> loadUsers() {
        Map<String, User> users = new HashMap<>();
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String[] headers = br.readLine().split(",");

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0) {

                    User user = switch (values[2]) {
                        case "Customer" -> new Customer(values[0], values[1], values[7], values[4], values[6], values[5]);
                        case "Staff" -> new Staff(values[0], values[1], values[7], values[4], values[6], values[5]);
                        case "Admin" -> new Admin(values[0], values[1], values[7], values[4], values[6], values[5]);
                        default -> throw new IllegalStateException("Unexpected value (loadUsers): " + values[2]);
                    };
                    users.put(values[0], user);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User verifyUser(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = "";

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0 && values[1].equals(username) && values[7].equals(password)) {
                    User user = switch (values[2]) {
                        case "Customer" -> new Customer(values[0], values[1], values[7], values[4], values[6], values[5]);
                        case "Staff" -> new Staff(values[0], values[1], values[7], values[4], values[6], values[5]);
                        case "Admin" -> new Admin(values[0], values[1], values[7], values[4], values[6], values[5]);
                        default -> throw new IllegalStateException("Unexpected value (loadUsers): " + values[2]);
                    };
                    return user;
                }
            }
        } catch (IOException e) {
            System.err.println("Error verifying user (verifyUser): " + e.getMessage());
        }
        return null;
    }

    public String generateUniqueUuid(){
        String uuid;
        do{
            uuid = UUID.randomUUID().toString();
        }while(isUuidExists(uuid));
        return uuid;
    }

    public boolean isUuidExists(String uuid) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = "";

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0 && values[0].equals(uuid)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error checking UUID (isUuidExists): " + e.getMessage());
        }
        return false;
    }
}