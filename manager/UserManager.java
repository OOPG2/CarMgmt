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
    private static final String csvFile = System.getProperty("user.dir") + "/src/main/java/org/example/CarMgmt/databases/users.csv";
    private static final String[] HEADERS = {"user_id", "username", "password", "type", "status", "name", "email", "phone", "is_banned"};

    static {
        initializeFile();
    }

    private static void initializeFile() {
        File file = new File(csvFile);
        try {
            file.getParentFile().mkdirs();

            if (!file.exists() || file.length() == 0) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(String.join(",", HEADERS));
                }
            }
        } catch (IOException e) {
            System.err.println("Error initializing users.csv: " + e.getMessage());
        }
    }

    public static void createUser(String username, String name, String email, String phone, String role) {
        if (username != null && name != null && email != null  && phone != null && role != null) {
            int membership = 0;
            if (role.equals("Customer"))
                membership = 1;
            String[] userInfo = new String[]{
                    generateUniqueUuid(),
                    username,
                    AuthenticationManager.defaultPassword,
                    role,
                    Integer.toString(membership),
                    name,
                    email,
                    phone,
                    Boolean.toString(false)
            };

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, true))) {
                if (new File(csvFile).length() > 0) {
                    writer.newLine();
                }
                writer.write(String.join(",", userInfo));
                System.out.println("Data appended to " + csvFile);
            } catch (IOException e) {
                System.err.println("Error writing to file (createUser): " + e.getMessage());
            }
        }
    }

    public static Map<String, User> getUsers() {
        Map<String, User> users = new HashMap<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                String[] values = line.split(",");
                if (values.length > 0) {
                    User user = switch (values[3]) {
                        case "Customer" -> new Customer(values[0], values[1], values[2], values[3], Integer.parseInt(values[4]), values[5], values[6], values[7], Boolean.parseBoolean(values[8]));
                        case "Staff" -> new Staff(values[0], values[1], values[2], values[3], Integer.parseInt(values[4]), values[5], values[6], values[7], Boolean.parseBoolean(values[8]));
                        case "Admin" -> new Admin(values[0], values[1], values[2], values[3], Integer.parseInt(values[4]), values[5], values[6], values[7], Boolean.parseBoolean(values[8]));
                        default -> throw new IllegalStateException("Unexpected value (loadUsers): " + values[3]);
                    };
                    users.put(values[0], user);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static User getUserById(String userId) {
        return getUsers().get(userId);
    }

    public static void updateUser(User user) {
        StringBuilder fileContent = new StringBuilder();
        boolean userFound = false;
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            fileContent.append(br.readLine()).append("\n");

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                String[] values = line.split(",");
                if (values.length > 0 && values[0].equals(user.getUserId())) {
                    line = String.join(",",
                            user.getUserId(),
                            user.getUsername(),
                            user.getPassword(),
                            user.getRole(),
                            Integer.toString(user.getMembership()),
                            user.getName(),
                            user.getEmail(),
                            user.getPhone(),
                            Boolean.toString(user.getIsBanned())
                    );
                    userFound = true;
                }
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading from file (updateUser): " + e.getMessage());
            return;
        }

        if (!userFound) {
            System.err.println("User not found.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write(fileContent.toString().trim());
        } catch (IOException e) {
            System.err.println("Error writing to file (updateUser): " + e.getMessage());
        }
    }

    public static void deleteUser(String userId) {
        StringBuilder fileContent = new StringBuilder();
        boolean userFound = false;
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            fileContent.append(br.readLine()).append("\n");

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                String[] values = line.split(",");
                if (values.length > 0 && values[0].equals(userId)) {
                    userFound = true;
                    continue;
                }
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading from file (deleteUser): " + e.getMessage());
            return;
        }

        if (!userFound) {
            System.err.println("User not found with ID: " + userId);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write(fileContent.toString().trim());
            System.out.println("User deleted successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file (deleteUser): " + e.getMessage());
        }
    }

    public static String generateUniqueUuid(){
        String uuid;
        do{
            uuid = UUID.randomUUID().toString();
        }while(isUuidExists(uuid));
        return uuid;
    }

    public static boolean isUuidExists(String uuid) {
        return getUsers().containsKey(uuid);
    }
}